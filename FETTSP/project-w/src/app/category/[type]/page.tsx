'use client';
import styles from "./page.module.css";
import { useCallback, useEffect, useState } from "react";
import ShowImageModal from "@/components/show-image.modal";
import { Category } from "@/type/category";
import { useParams } from "next/navigation";
import Link from "next/link";
export default function Type() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
    // const url2 = 'localhost:3000';
    const [linkUrl, setLink] = useState<string>("");
    const [showImgModal, setShowImgModal] = useState<boolean>(false);

    const { type } = useParams();
    const [categoryType, setCategoryType] = useState<string>();
    const [categoryId, setCategoryId] = useState<number>(0);
    const [category, setCategory] = useState<Category>();

    useEffect(() => {
        setCategoryType(Array.isArray(type) ? type[0] : type);
    }, [type]);

    useEffect(() => {
        if (categoryType) {
            switch (categoryType.toLowerCase()) {
                case 'comic':
                    setCategoryId(1);
                    break;
                case 'digital':
                    setCategoryId(2);
                    break;
                case 'printed-art':
                    setCategoryId(3);
                    break;
                case 'sketch':
                    setCategoryId(4);
                    break;
                case 'water-color':
                    setCategoryId(5);
                    break;
                default:
                    setCategoryId(0);
            }
        }
    }, [categoryType]);

    useEffect(() => {
        if (categoryId > 0) {
            fetch(`http://${url}/api/category/one/${categoryId}`)
                .then((res) => res.json())
                .then((data) => {
                    setCategory(data);
                    console.log('data', data);
                })
                .catch((error) => console.log('No response', error));
        }
    }, [categoryId]);

    return (
        <div className={`container ${styles.customContainer}`}>
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <div className={styles['site-content__list-image']}>
                            <div className={styles['grid-main']}>
                                {category?.productDTOs.map((product) => (
                                    <div key={product.id} className={styles['grid-item']} >
                                        <Link href={`http://${url}/category/${categoryType}/picture/${product.id}`}>
                                            <img src={product.imageDTOs[0].url} alt=""
                                                onClick={() => {
                                                }} style={{ maxWidth: "100%", padding: "5px" }}
                                            />
                                        </Link>
                                    </div>))}
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