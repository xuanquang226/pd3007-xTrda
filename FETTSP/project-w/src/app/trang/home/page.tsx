'use client';
import Link from "next/link";
import styles from "./page.module.css";
import ShowImageModal from '../../../components/show-image.modal'
import { useEffect, useState } from "react";
import { Image } from "@/type/image";
export default function homeTrang() {
    const [linkImg, setLinkImg] = useState<string>("");
    const [showImgModal, setShowImgModal] = useState<boolean>(false);
    const [images, setImages] = useState<Image[]>();

    useEffect(() => {
        // const newDiv = document.createElement('div');
        // const sidebarNav = document.querySelector('#sidebar-nav');
        // if (sidebarNav) sidebarNav.append(newDiv);
        // if (sidebarNav) sidebarNav.classList.add("asdasd");
        // const linkHome = document.getElementById('link-home');
        // if (linkHome) linkHome.setAttribute("hidden", "false");

        fetch("http://localhost:8082/images/many")
            .then(res => res.json())
            .then(data => setImages(data));
    }, []);



    return (
        <div className={`container ${styles.customContainer}`}>
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    {/* <div className={styles['sidebar']}>
                        <header className={styles['sidebar__logo']}>
                            <img src="../images/avatar.jpg" alt="" className={styles['sidebar__logo_img']} style={{ maxWidth: "100%", maxHeight: "100%" }} />
                        </header>
                        <nav id="sidebar-nav" className={styles['sidebar__nav']}>
                            <Link id="link-home" href="home" className={styles['sidebar__nav__link']}>Home</Link>
                            <Link href="digital" className={styles['sidebar__nav__link']}>Digital</Link>
                            <Link href="watercolor" className={styles['sidebar__nav__link']}>Water color</Link>
                            <Link href="comic" className={styles['sidebar__nav__link']}>Comic</Link>
                            <Link href="sketch" className={styles['sidebar__nav__link']}>Sketch</Link>
                            <Link href="printed-art" className={styles['sidebar__nav__link']}>Printed art</Link>
                            <Link href="contact-me" className={styles['sidebar__nav__link']}>Contact me</Link>
                        </nav>
                    </div> */}
                    <div className={styles['site-content']}>
                        <div className={styles['site-content__list-image']}>
                            <div className={styles['grid-main']}>
                                {images?.map((image) => (
                                    <div className={styles['grid-item']} >
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