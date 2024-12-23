'use client'
import { Product } from "@/type/product";
import { useParams } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import styles from "./page.module.css";
import Link from "next/link";
import { Button } from "react-bootstrap";
import CartItem from "@/type/cart-item";
import useCartStore from "@/app/store/state-cart";
import fetchWithToken from "@/utils/fetchWithToken";
import { notifyError, notifySuccess } from "@/utils/notify";
import classNames from "classnames";
import { ToastContainer } from "react-toastify";
export default function ProductDetail() {
    const { type, id } = useParams();
    const { cartItemStore, addCartItem } = useCartStore();
    const [product, setProduct] = useState<Product>();
    const [urlImage, setUrlImage] = useState<string>();
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';

    const [autoRetry, setAutoRetry] = useState<boolean>(false);

    const [cartItem, setCartItem] = useState<CartItem>({
        id: 0,
        idCart: 0,
        idProduct: 0,
        quantity: 1,
        price: '',
        note: '',
        name: ''
    }
    );

    // get product from param id
    const getProductFromId = useCallback(async () => {
        const response = await fetchWithToken(`https://${url}/api/product/${id}?categoryType=${type}`, {
            method: 'GET',
        }, autoRetry);
        try {
            if (response && response.ok) {
                const data = await response.json();
                if (data != null) {
                    setProduct(data);
                    if (urlImage === undefined) {
                        setUrlImage(data.imageDTOs[0].url);
                    };
                } else {
                    console.log("data is null because url invalid");
                }
            } else {
                throw new Error(`Failed to fetch ${response?.status}`);
            }
        } catch (error) {
            console.log(error);
        }

    }, [id]);

    useEffect(() => {
        getProductFromId();
    }, [getProductFromId]);

    // handle on click mini image
    const handleChooseImage = (urlImage: string) => {
        setUrlImage(urlImage);
    };

    const handleChangeQuantity = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newQuantity = Number(event.target.value);
        if (event.target) setCartItem({ ...cartItem, quantity: newQuantity })
    }

    // handle on click add cart
    const handleAddCart = () => {
        if (isAvailable) {
            const newCartItem = { ...cartItem };
            newCartItem.idProduct = product?.id ?? 0;
            fetchWithToken(`https://${url}/api/cart-item`, {
                method: 'POST',
                body: JSON.stringify(newCartItem)
            }, autoRetry);
            addCartItem(newCartItem);
            notifySuccess('Thêm hàng thành công');
        } else {
            notifyError('Hết hàng');
            console.log("Product is unavailable");
        }
    };



    const [isAvailable, setIsAvailable] = useState<boolean>(true);
    const checkQuantityOfProduct = useCallback(() => {
        if (product && product.quantity < 1) {
            setIsAvailable(false);
            setCartItem({ ...cartItem, quantity: 0 });
        }
    }, [product]);

    useEffect(() => {
        checkQuantityOfProduct();
    }, [checkQuantityOfProduct]);

    return (
        <div className={`container ${styles.customContainer}`}>
            <ToastContainer />
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']} style={!isAvailable ? { opacity: 0.55 } : {}}>
                        <div className={styles['left-content']}>
                            <Link href="#" className={styles['left-content__link']}>
                                <img src={urlImage} alt="" />
                            </Link>
                            <div className={styles['list-images']}>
                                <ul>
                                    {product?.imageDTOs.map((image) => (
                                        <li key={image.id}>
                                            <img src={image.url} alt="" onClick={() => handleChooseImage(image.url)} style={{ width: "75px", height: "75px" }} />
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                        <div className={styles['right-content']}>
                            <div className="top">
                                <p>{product?.name}</p>
                                <p>{product?.description}</p>
                                <p>{product?.price}</p>
                            </div>
                            <div className={styles['bottom']}>
                                <input type="number" value={cartItem.quantity} style={{ width: '50px' }} min={isAvailable ? "1" : "0"} max={isAvailable ? product?.quantity : "0"} onChange={handleChangeQuantity} />
                                <Button variant="primary" onClick={handleAddCart}>Thêm hàng</Button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}