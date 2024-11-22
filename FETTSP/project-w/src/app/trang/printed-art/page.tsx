'use client';
import styles from "./page.module.css";
import { useEffect, useState } from "react";
import ShowImageModal from "@/components/show-image.modal";
import { Image } from "@/type/image";
import { Category } from "@/type/category";
import Link from "next/link";
import classNames from "classnames";
import { Button } from "react-bootstrap";
export default function comic() {
    const [linkUrl, setLink] = useState<string>("");
    const [showImgModal, setShowImgModal] = useState<boolean>(false);
    const [images, setImages] = useState<Image[]>([]);
    const [category, setCategory] = useState<Category>();

    useEffect(() => {
        fetch("http://localhost:8082/category/one/3")
            .then(res => res.json())
            .then(data => {
                setCategory(data);
                console.log('du lieu', data)
            })
            .catch(error => console.log('Khong nhan response', error));

    }, []);

    useEffect(() => {
        if (category) {
            const newImages = category.productDTOs.map(product => product.imageDTOs[0]);
            setImages(newImages);
        }
    }, [category]);

    return (
        <div className={`container ${styles.customContainer}`}>
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <div className={styles['site-content__list-image']}>
                            <div className={styles['grid-main']}>
                                {images?.map((image) => (
                                    <div className={styles['grid-item']} >
                                        <Link href="#" ><img src={image.url} alt=""
                                            onClick={() => {
                                                setLink(image.url);
                                                setShowImgModal(true);
                                            }} style={{
                                                width: '500px', height: '500px',
                                                position: 'relative', left: '50%', transform: "translateX(-50%)"
                                            }}
                                        /></Link>
                                        <div className={classNames(styles['input-group'])}>
                                            <Button variant="primary">Thêm giỏ hàng</Button>
                                            <input type="number" placeholder="1" style={{ width: '50px' }} min="1" max="100" />
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            </div >
            <ShowImageModal
                linkImg={linkUrl}
                showImgModal={showImgModal}
                setShowImgModal={setShowImgModal}
            ></ShowImageModal>
        </div>
    )
}