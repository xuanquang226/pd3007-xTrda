'use client'

import Link from "next/link";
import styles from "../styles/top-navigation.module.css"
import { useCallback, useEffect, useRef, useState } from "react";
import Cart from "@/type/cart";
import classNames from "classnames";
import { Button } from "react-bootstrap";
import { Product } from "@/type/product";

export default function TopNavigation() {
    const [isPopupVisible, setIsPopupVisible] = useState(false);
    const showPopup = () => setIsPopupVisible(true);
    const hidePopup = () => setIsPopupVisible(false);

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

    const [idCustomer, setIdCustomer] = useState<Number>();
    const getCart = useCallback(async () => {
        const response = await fetch("http://localhost:8082/cart/1", {
            method: 'GET'
        });
        if (response.ok) {
            const data = await response.json();
            setCart(data);
        } else {
            console.error('Failed to fetch');
        }
    }, []);

    useEffect(() => {
        getCart();
    }, []);



    // Get url image
    const idProductUrlMap = new Map();
    const [productList, setProductList] = useState<Product[]>([]);
    productList.forEach(product => {
        idProductUrlMap.set(product.id, product.imageDTOs[0].url);
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
    const [quantities, setQuantities] = useState<Map<number, number>>();
    const createQuantities = useCallback(() => {
        const idProductQuantityMap = new Map();
        cart.listCartItem.forEach(cartItem => {
            idProductQuantityMap.set(cartItem.idProduct, cartItem.quantity);
        });
        setQuantities(idProductQuantityMap);
    }, [cart]);
    useEffect(() => {
        createQuantities();
    }, [createQuantities]);

    const decreaseQuantityItem = (idProduct: number) => {
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
            return newQuantities;
        });
    };

    const increaseQuantityItem = (idProduct: number) => {
        setQuantities((oldQuantities) => {
            const newQuantities = new Map(oldQuantities);
            if (!newQuantities.has(idProduct)) {
                return oldQuantities;
            }
            const quantity = newQuantities.get(idProduct);
            const newQuantity = (quantity || 0) + 1;
            newQuantities.set(idProduct, newQuantity);
            return newQuantities;
        });
    }

    return (
        <div className={styles['top-navigation']}>
            <div className={styles['nav-icons']} onMouseEnter={showPopup} onMouseLeave={hidePopup}>
                <Link href="" className={styles['icon']}>
                    <img src="../images/cart.png" alt="cart"></img>
                </Link>
                <Link href="" className={styles['icon']}>
                    <img src="../images/account.png" alt="account"></img>
                </Link>

                <div className={styles['popup']} style={{ visibility: isPopupVisible ? "visible" : "hidden" }}>
                    <div className={styles['popup-content']}>
                        <div className={styles['cart-item-list']} style={{ overflowY: "auto", maxHeight: "90%", backgroundColor: "#ebe6e5" }}>
                            <ul style={{ padding: 0, margin: 0 }}>
                                {cart.listCartItem.map(cartItem => {
                                    return (<li key={cartItem.id}>
                                        <div className={styles['cart-item']}>
                                            <Link href="#"><img src={idProductUrlMap.get(cartItem.idProduct)} style={{ width: "50px", height: "50px" }} alt="" className={classNames(styles['cart-item__img'], ['cart-item__img--product'])} /></Link>
                                            <div className={styles['info-cart-item']}>
                                                <p>{cartItem.name}</p>
                                                <p>{cartItem.note}</p>
                                                <div className={styles['info2-cart-item']}>
                                                    <div className={styles['price-cart-item']}>
                                                        <p>{cartItem.price}</p>
                                                        <p>VNĐ</p>
                                                    </div>
                                                    <div className={styles['quantity']}>
                                                        <Link href="#" onClick={() => { decreaseQuantityItem(cartItem.idProduct) }}><img src="../images/decrease.png" alt="" /></Link>
                                                        <p>{quantities?.get(cartItem.idProduct)}</p>
                                                        <Link href="#" onClick={() => { increaseQuantityItem(cartItem.idProduct) }}><img src="../images/increase.png" alt="" /></Link>
                                                    </div>
                                                </div>
                                            </div>
                                            <Link href="#"><img src="../images/delete.png" alt="" className={classNames(styles['cart-item__img'], ['cart-item__img--icon-delete'])} /></Link>
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