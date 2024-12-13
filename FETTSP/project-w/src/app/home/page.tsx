'use client';
import Link from "next/link";
import styles from "./page.module.css";
import ShowImageModal from '@/components/show-image.modal'
import { Suspense, useEffect, useState } from "react";
import { Image } from "@/type/image";
import { useRouter, useSearchParams } from "next/navigation";
export default function HomeTrang() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    const [linkImg, setLinkImg] = useState<string>("");
    const [showImgModal, setShowImgModal] = useState<boolean>(false);
    const [images, setImages] = useState<Image[]>();

    useEffect(() => {
        fetch(`http://${url}:8082/images/many`)
            .then(res => res.json())
            .then(data => setImages(data));
    }, []);

    const searchParams = useSearchParams();
    const reload = searchParams.get('reload');

    useEffect(() => {
        // const reload = new URLSearchParams(window.location.search).get('reload');
        if (reload === 'true') {
            window.location.replace('/home');
        }
    }, [reload]);

    return (
        <div className={`container ${styles.customContainer}`}>
            <div className={styles['site-container']}>
                <div className={styles['site-content']}>
                    <div className={styles['site-content__list-image']}>
                        <div className={styles['grid-main']}>
                            {images?.map((image) => (
                                <div className={styles['grid-item']} key={image.id}>
                                    <img src={image.url} alt=""
                                        onClick={() => {
                                            setLinkImg(image.url);
                                            setShowImgModal(true);
                                        }} style={{ maxWidth: "100%", maxHeight: "100%" }}
                                    />
                                </div>))}
                        </div>
                    </div>
                </div>
            </div >
            <ShowImageModal
                linkImg={linkImg}
                showImgModal={showImgModal}
                setShowImgModal={setShowImgModal}
            ></ShowImageModal>
        </div>
    )
}

const SuspendedHomePage = () => (
    <Suspense fallback={<div>Loading...</div>}>
        <HomeTrang />
    </Suspense>
);