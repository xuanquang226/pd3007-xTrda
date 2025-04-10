'use client';
import { useEffect, useState } from "react";
import { Category } from "@/type/category";
import styles from "./page.module.css";
import ShowImageModal from "@/components/show-image.modal";

export default function Reference(){
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';

    const [linkImg, setLinkImg] = useState<string>("");
    const [showImgModal, setShowImgModal] = useState<boolean>(false);

    const [category, setCategory] = useState<Category>();

    useEffect(() => {
        fetch(`https://${url}/api/category/one/6`)
            .then((res) => res.json())
            .then((data) => {
                setCategory(data)
            })
            .catch((error) => {console.log('No response', error)})
    }, 
    []);

    return(
        <div className={`container ${styles.customContainer}`}>
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <div className={styles['site-content__list-image']}>
                            <div className={styles['grid-main']}>
                                {category?.productDTOs[0].imageDTOs.map((image) => (
                                    <div key={image.id} className={styles['grid-item']}>
                                        <img src={image.url} alt=""
                                                onClick={() => {
                                                    setLinkImg(image.url ?? '');
                                                    setShowImgModal(true);
                                                }} style={{ maxWidth: "100%", padding: "5px" }}
                                            />
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
             <ShowImageModal
                linkImg={linkImg}
                showImgModal={showImgModal}
                setShowImgModal={setShowImgModal}
            ></ShowImageModal>
        </div>
    );
}