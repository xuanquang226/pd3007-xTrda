'use client'
import { Product } from "@/type/product";
import { useParams } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import styles from "./page.module.css";
import Link from "next/link";
import { Button } from "react-bootstrap";
import CartItem from "@/type/cart-item";
export default function ProductDetail() {
    const { type, id } = useParams();
    const [product, setProduct] = useState<Product>();
    const [urlImage, setUrlImage] = useState<string>();

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

    // get cart from param id
    const getProductFromId = useCallback(async () => {
        const response = await fetch(`http://localhost:8082/product/${id}`, {
            method: 'GET',
        });
        try {
            if (response.ok) {
                const data = await response.json();
                setProduct(data);
                if (urlImage === undefined) {
                    setUrlImage(data.imageDTOs[0].url);
                };
            } else {
                throw new Error(`Failed to fetch ${response.status}`);
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
        const newCartItem = { ...cartItem };
        newCartItem.idProduct = product?.id ?? 0;
        newCartItem.idCart = 2;
        fetch("http://localhost:8082/cart-item", {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(newCartItem)
        });

        fetch("http://localhost:8082/cart/2", {
            method: 'GET'
        });
        return newCartItem
    };

    return (
        <div className={`container ${styles.customContainer}`}>
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <div className={styles['left-content']}>
                            <Link href="#" className={styles['left-content__link']}>
                                <img src={urlImage} style={{ width: '100%', height: '400px' }} alt="" />
                            </Link>
                            <div className={styles['list-images']}>
                                <ul>
                                    {product?.imageDTOs.map((image) => (
                                        <li>
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
                            </div>
                            <div className={styles['bottom']}>
                                <input type="number" placeholder="1" value={cartItem.quantity} style={{ width: '50px' }} min="1" max="100" onChange={handleChangeQuantity} />
                                <Button variant="primary" onClick={handleAddCart}>Thêm hàng</Button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}