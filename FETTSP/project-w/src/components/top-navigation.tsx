'use client'

import Link from "next/link";
import styles from "../styles/top-navigation.module.css"
import { useCallback, useEffect, useRef, useState } from "react";
import Cart from "@/type/cart";
import classNames from "classnames";
import { Button } from "react-bootstrap";
import { Product } from "@/type/product";
import CartItem from "@/type/cart-item";
import useCartStore from "@/app/store/state-cart";

export default function TopNavigation() {
    const [isPopupVisible, setIsPopupVisible] = useState(false);
    const showPopup = () => setIsPopupVisible(true);
    const hidePopup = () => setIsPopupVisible(false);

    //State chung cap nhat o component chi tiet san pham
    const { cartItemStore, addCartItem } = useCartStore();

    // Get cart
    const [cart, setCart] = useState<Cart>({
        id: 0,
        idCustomer: 0,
        status: '',
        totalPrice: '',
        codeDiscount: '',
        notes: '',
        listCartItem: []
    });
    const [cartItem, setCartItem] = useState<CartItem>({
        id: 0,
        idCart: 0,
        idProduct: 0,
        quantity: 0,
        price: null,
        note: null,
        name: null
    });

    //TODO: Xu ly dang nhap lay id
    const [idCustomer, setIdCustomer] = useState<Number>();
    const getCart = useCallback(async () => {
        await delay(600);
        const response = await fetch("http://localhost:8082/cart/2", {
            method: 'GET'
        });
        if (response.ok) {
            const data = await response.json();
            setCart(data);
        } else {
            console.error('Failed to fetch');
        }
    }, [cartItem]);

    useEffect(() => {
        getCart();
    }, [getCart]);

    useEffect(() => {
        setCartItem(cartItemStore);
    }, [cartItemStore]);

    // Create map idProduct,Product from productList
    const idProductToProductMap = new Map<number, Product>();
    const [productList, setProductList] = useState<Product[]>([]);
    productList.forEach(product => {
        idProductToProductMap.set(product.id, product);
    });
    const getProductById = useCallback(async () => {
        const idProductList: number[] = new Array();
        cart.listCartItem.forEach(cartItem => {
            idProductList.push(cartItem.idProduct);
        });
        const response = await fetch(`http://localhost:8082/product/many`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(idProductList)
        });
        if (response.ok) {
            const data = await response.json();
            setProductList(data);
        }
    }, [cart]);
    useEffect(() => {
        getProductById();
    }, [getProductById]);



    // Handle quantity of item
    const [quantities, setQuantities] = useState<Map<number, number>>(new Map());
    const createQuantities = useCallback(() => {
        const idProductToQuantityMap = new Map();
        cart.listCartItem.forEach(cartItem => {
            idProductToQuantityMap.set(cartItem.idProduct, cartItem.quantity);
        });
        setQuantities(idProductToQuantityMap);
    }, [cart]);
    useEffect(() => {
        createQuantities();
    }, [createQuantities]);

    const decreaseQuantityItem = (idProduct: number, idCart: number) => {
        setQuantities((oldQuantities) => {
            const newQuantities = new Map(oldQuantities);
            if (!newQuantities.has(idProduct)) {
                return oldQuantities;
            }
            const quantity = newQuantities.get(idProduct);
            if (quantity === undefined || quantity <= 0) {
                return oldQuantities;
            }
            const newQuantity = (quantity || 0) - 1;
            newQuantities.set(idProduct, newQuantity);
            handleTotalPrice(idProduct, idCart, newQuantity);
            return newQuantities;
        });
    };

    const increaseQuantityItem = (idProduct: number, idCart: number) => {
        setQuantities((oldQuantities) => {
            const newQuantities = new Map(oldQuantities);
            if (!newQuantities.has(idProduct)) {
                return oldQuantities;
            }
            const quantity = newQuantities.get(idProduct);
            const newQuantity = (quantity || 0) + 1;
            newQuantities.set(idProduct, newQuantity);
            handleTotalPrice(idProduct, idCart, newQuantity);
            return newQuantities;
        });
    }


    //Handle total price
    const handleTotalPrice = (idProduct: number, idCart: number, quantity: number) => {
        setCartItem((oldCartItem) => {
            const newCartItem = { ...oldCartItem, idProduct: idProduct, idCart: idCart, quantity: quantity };
            fetch(`http://localhost:8082/cart-item`, {
                method: 'PUT',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(newCartItem)
            });
            return newCartItem;
        })
    };


    //Handle delete item
    const handleDeleteItem = (idCartItem: number) => {
        fetch(`http://localhost:8082/cart-item/${idCartItem}`, {
            method: 'DELETE',
        });
        setCartItem({ ...cartItem, id: idCartItem });
    };


    //Handle quantity cart
    const [quantityCart, setQuantityCart] = useState<number>(0);
    const handleQuantityCart = useCallback(() => {
        let quantityCart = 0;
        quantities.forEach(quantity => {
            quantityCart += quantity;
        })
        setQuantityCart(quantityCart);
    }, [quantities])

    useEffect(() => {
        handleQuantityCart();
    }, [handleQuantityCart]);


    return (
        <div className={styles['top-navigation']}>
            <div className={styles['nav-icons']} onMouseEnter={showPopup} onMouseLeave={hidePopup}>
                <Link href="" className={styles['icon']}>
                    <img src="/images/cart.png" alt="cart"></img>
                </Link>
                <p>{quantityCart}</p>
                <Link href="" className={styles['icon']}>
                    <img src="/images/account.png" alt="account"></img>
                </Link>

                <div className={styles['popup']} style={{ visibility: isPopupVisible ? "visible" : "hidden" }}>
                    <div className={styles['popup-content']}>
                        <div className={styles['cart-item-list']} style={{ overflowY: "auto", maxHeight: "90%", backgroundColor: "#ebe6e5" }}>
                            <ul style={{ padding: 0, margin: 0 }}>
                                {cart.listCartItem.map(cartItem => {
                                    return (<li key={cartItem.id}>
                                        <div className={styles['cart-item']}>
                                            <Link
                                                href={convertIdCategoryToNameCategory((idProductToProductMap.get(cartItem.idProduct)?.idCategory), cartItem.idProduct) ?? ''}
                                            >
                                                <img src={idProductToProductMap.get(cartItem.idProduct)?.imageDTOs[0].url} style={{ width: "50px", height: "50px" }} alt="" className={classNames(styles['cart-item__img'], ['cart-item__img--product'])} />
                                            </Link>
                                            <div className={styles['info-cart-item']}>
                                                <p>{cartItem.name}</p>
                                                <p>{cartItem.note}</p>
                                                <div className={styles['info2-cart-item']}>
                                                    <div className={styles['price-cart-item']}>
                                                        <p>{idProductToProductMap.get(cartItem.idProduct)?.price}</p>
                                                        <p>VNĐ</p>
                                                    </div>
                                                    <div className={styles['quantity']}>
                                                        <Link href="#" onClick={() => { decreaseQuantityItem(cartItem.idProduct, cartItem.idCart) }}><img src="/images/decrease.png" alt="" /></Link>
                                                        <p>{quantities?.get(cartItem.idProduct)}</p>
                                                        <Link href="#" onClick={() => { increaseQuantityItem(cartItem.idProduct, cartItem.idCart) }}><img src="/images/increase.png" alt="" /></Link>
                                                    </div>
                                                </div>
                                            </div>
                                            <Link href="#" onClick={() => { handleDeleteItem(cartItem.id) }}><img src="/images/delete.png" alt="" className={classNames(styles['cart-item__img'], ['cart-item__img--icon-delete'])} /></Link>
                                        </div>
                                    </li>
                                    )
                                })}
                            </ul>
                        </div>
                        <div className={styles['cart-info']}>
                            <div className={styles['total-price']}>
                                <p>Total price: </p>
                                <p>{cart.totalPrice}</p>
                                <p>VNĐ</p>
                            </div>
                            <Button href="#" className={classNames(styles['cart-info__button'])} variant="dark">Checkout</Button>
                        </div>
                    </div>
                </div>
            </div>
        </div >
    );
}

function delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

const convertIdCategoryToNameCategory = (idCategory: number | undefined, idProduct: number) => {
    switch (idCategory) {
        case 1:
            return `/category/comic/picture/${idProduct}`;
        case 2:
            return `/category/digital/picture/${idProduct}`;
        case 3:
            return `/category/printed-art/picture/${idProduct}`;
        case 4:
            return `/category/sketch/picture/${idProduct}`;
        case 5:
            return `/category/water-color/picture/${idProduct}`;
        default:
            return undefined;
    }
}