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
import fetchWithToken from "@/utils/fetchWithToken";
import Customer from "@/type/customer";
import UserInterface from "@/components/user-interface";
import { useRouter } from "next/navigation";
import useUserStore from "@/app/store/state-user";

export default function TopNavigation() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    //State chung cap nhat o component chi tiet san pham
    const { cartItemStore, addCartItem } = useCartStore();
    const { customerStore, addCustomer } = useUserStore();
    const [autoRetry, setAutoRetry] = useState<boolean>(false);
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
    const defaultCartItem = {
        id: 0,
        idCart: 0,
        idProduct: 0,
        quantity: 0,
        price: null,
        note: null,
        name: null
    };
    const [cartItem, setCartItem] = useState<CartItem>(defaultCartItem);

    const getCart = useCallback(async () => {
        await delay(600);
        const response = await fetchWithToken(`http://${url}:8080/api/cart`, {
            method: 'GET'
        }, autoRetry);
        if (response && response.ok) {
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

    const [isPopupVisible, setIsPopupVisible] = useState(false);
    const showPopup = () => {
        if (cart.id !== 0) {
            if (isPopupVisible === true) {
                setIsPopupVisible(false);
            } else {
                setIsPopupVisible(true);
                setIsPopupUserVisible(false);
            }
        } else {
            setIsPopupVisible(false);
        }
    };

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
        const response = await fetchWithToken(`http://${url}:8080/api/product/many`, {
            method: 'POST',
            body: JSON.stringify(idProductList)
        }, autoRetry);
        if (response && response.ok) {
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
            fetchWithToken(`http://${url}:8080/api/cart-item`, {
                method: 'PUT',
                body: JSON.stringify(newCartItem)
            }, autoRetry);
            return newCartItem;
        })
    };


    //Handle delete item
    const handleDeleteItem = (idCartItem: number) => {
        fetchWithToken(`http://${url}:8080/api/cart-item/${idCartItem}`, {
            method: 'DELETE',
        }, autoRetry);
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


    // get info customer after signin
    const [customer, setCustomer] = useState<Customer>({
        id: 0,
        name: '',
        mail: '',
        location: '',
        phone: '',
        idAccount: 0
    });

    const getCustomer = useCallback(async () => {
        const response = await fetchWithToken(`http://${url}:8080/api/customer`, {
            method: 'GET'
        }, autoRetry);
        if (response && response.ok) {
            const data = await response.json();
            setCustomer(data);
            addCustomer(data);
        } else {
            console.log('Failed to fetch');
        }
    }, [cart, autoRetry]);

    useEffect(() => {
        getCustomer();
    }, [getCustomer]);


    //Hide popup when click container
    const [isPopupUserVisible, setIsPopupUserVisible] = useState<boolean>(false);
    const popupUserRef = useRef<HTMLAnchorElement>(null);
    const popupRef = useRef<HTMLAnchorElement>(null);
    const divPopupRef = useRef<HTMLDivElement>(null);

    const showPopupUser = () => {
        if (customer.id !== 0) {
            if (isPopupUserVisible === true) {
                setIsPopupUserVisible(false);
            } else {
                setIsPopupUserVisible(true);
                setIsPopupVisible(false);
            }
        } else {
            setIsPopupUserVisible(false);
            setAutoRetry(true);
        }
    };

    const handleClickOutSide = (event: MouseEvent) => {
        if ((popupRef.current && !popupRef.current.contains(event.target as HTMLElement))
            && (divPopupRef.current && !divPopupRef.current.contains(event.target as HTMLElement))) {
            setIsPopupVisible(false);
        }

        if (popupUserRef.current && !popupUserRef.current.contains(event.target as HTMLElement)) {
            setIsPopupUserVisible(false);
        }
    };

    useEffect(() => {
        window.addEventListener("click", handleClickOutSide);
        return () => {
            window.removeEventListener("click", handleClickOutSide);
        };
    }, [isPopupUserVisible, isPopupVisible]);


    const handleClickCheckOut = async () => {
        const responseOrder = await fetchWithToken(`http://${url}:8080/api/order`, {
            method: 'POST',
        }, autoRetry);
        if (responseOrder && responseOrder.ok) {
            const responseCartAfterOrder = await fetchWithToken(`http://${url}:8080/api/cart/after-order`, {
                method: 'GET',
            }, autoRetry);
            if (responseCartAfterOrder && responseCartAfterOrder.ok) {
                const data = await responseCartAfterOrder.json();
                setCart(data);
                setCartItem(defaultCartItem);
            }
        }
    }

    const noAction = () => {

    };

    return (
        <div className={styles['top-navigation']}>
            <div className={styles['nav-icons']} >
                <Link href={cart.id !== 0 ? '#' : '/sign-in'} ref={popupRef} className={styles['icon']} onClick={showPopup}>
                    <img src="/images/cart.png" alt="cart"></img>
                </Link>
                <p>{quantityCart}</p>
                <Link href={customer.id !== 0 ? '#' : '/sign-in'} ref={popupUserRef} className={styles['icon']} onClick={showPopupUser}>
                    <img src="/images/account.png" alt="account"></img>
                </Link>
                <p>{customer.name}</p>

                <UserInterface info={customer} isPopupUserVisible={isPopupUserVisible}></UserInterface>

                <div className={styles['popup']} style={{ visibility: isPopupVisible ? "visible" : "hidden" }}>
                    <div ref={divPopupRef} className={styles['popup-content']}>
                        <div className={styles['cart-item-list']} style={{ overflowY: "auto", maxHeight: "90%", backgroundColor: "#ebe6e5" }}>
                            <ul style={{ padding: 0, margin: 0 }}>
                                {cart.listCartItem.map(cartItem => {
                                    const product = idProductToProductMap.get(cartItem.idProduct);
                                    let productIsAvailable = false;
                                    if (product && product.quantity && product.quantity >= 1) {
                                        productIsAvailable = true;
                                    }
                                    return (<li key={cartItem.id} style={productIsAvailable ? {} : { opacity: 0.55 }}>
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
                                                        <Link href="#" onClick={() => { productIsAvailable ? decreaseQuantityItem(cartItem.idProduct, cartItem.idCart) : noAction }}><img src="/images/decrease.png" alt="" /></Link>
                                                        <p>{quantities?.get(cartItem.idProduct)}</p>
                                                        <Link href="#" onClick={() => { productIsAvailable ? increaseQuantityItem(cartItem.idProduct, cartItem.idCart) : noAction }}><img src="/images/increase.png" alt="" /></Link>
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
                            <Button href="#" onClick={handleClickCheckOut} className={classNames(styles['cart-info__button'])} variant="dark">Checkout</Button>
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