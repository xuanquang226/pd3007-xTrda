'use client';
import Link from "next/link";
import styles from "./page.module.css";
import ShowImageModal from '@/components/show-image.modal'
import { Suspense, useEffect, useState } from "react";
import { Image } from "@/type/image";
import { useRouter, useSearchParams } from "next/navigation";
export default function HomeTrang() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
    const [linkImg, setLinkImg] = useState<string>("");
    const [showImgModal, setShowImgModal] = useState<boolean>(false);
    const [images, setImages] = useState<Image[]>([]);

    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);

    const fetchProducts = async () => {
        if (loading) return; // Không gọi API khi đang tải dữ liệu
        setLoading(true);

        try {
            const response = await fetch(`https://${url}/api/images/many2?page=${page}&size=8`);
            const data = await response.json();
            const newImages = data.content;
            setImages((prev) => [...prev, ...newImages]);
            setHasMore(data.hasNext);
            setPage((prev) => prev + 1);
        } catch (error) {
            console.error("Kiểm tra request", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    useEffect(() => {
        const handleScroll = () => {
            if (
                window.innerHeight + document.documentElement.scrollTop !==
                document.documentElement.offsetHeight ||
                loading
            )
                return;
            fetchProducts();
        };

        window.addEventListener("scroll", handleScroll);

        return () => window.removeEventListener("scroll", handleScroll);
    }, [loading]);

    // useEffect(() => {
    //     fetch(`https://${url}/api/images/many`)
    //         .then(res => res.json())
    //         .then(data => setImages(data));
    // }, []);

    const searchParams = useSearchParams();
    const reload = searchParams.get('reload');

    useEffect(() => {
        if (reload === 'true') {
            window.location.replace('/');
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
                                        }} style={{ maxWidth: "100%", padding: "5px" }}
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