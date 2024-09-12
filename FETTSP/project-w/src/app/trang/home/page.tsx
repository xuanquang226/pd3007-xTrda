'use client';
import Link from "next/link";
import styles from "./page.module.css";
import ShowImageModal from '../../../components/show-image.modal'
import { useEffect, useState } from "react";
export default function homeTrang() {
    const [linkImg, setLinkImg] = useState<string>("");
    const [showImgModal, setShowImgModal] = useState<boolean>(false);


    useEffect(() => {
        // const newDiv = document.createElement('div');
        // const sidebarNav = document.querySelector('#sidebar-nav');
        // if (sidebarNav) sidebarNav.append(newDiv);
        // if (sidebarNav) sidebarNav.classList.add("asdasd");
        // const linkHome = document.getElementById('link-home');
        // if (linkHome) linkHome.setAttribute("hidden", "false");

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
                                <div className={styles['grid-item']} >
                                    <img src="../images/01.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/01.jpg");
                                            setShowImgModal(true);
                                        }} style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }}
                                    />
                                </div>
                            </div>
                            <div className={styles['grid-main']}>
                                <div className={styles['grid-item']} >
                                    <img src="../images/02.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/02.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{
                                            objectFit: "contain", maxWidth: "100%", maxHeight: "100%",
                                        }} />
                                </div>
                                <div className={styles['grid-item']} >
                                    <img src="../images/03.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/03.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                            </div>

                            <div className={styles['grid-main']}>
                                <div className={styles['grid-item']}>
                                    <img src="../images/04.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/04.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                                <div className={styles['grid-item']}>
                                    <img src="../images/05.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/05.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                            </div>

                            <div className={styles['grid-main']}>
                                <div className={styles['grid-item']}>
                                    <img src="../images/06.jpg"
                                        onClick={() => {
                                            setLinkImg("../images/06.jpg");
                                            setShowImgModal(true);
                                        }}
                                        alt="" style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                                <div className={styles['grid-item']}>
                                    <img src="../images/07.jpg"
                                        onClick={() => {
                                            setLinkImg("../images/07.jpg");
                                            setShowImgModal(true);
                                        }}
                                        alt="" style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                                <div className={styles['grid-item']}>
                                    <img src="../images/08.jpg"
                                        onClick={() => {
                                            setLinkImg("../images/08.jpg");
                                            setShowImgModal(true);
                                        }}
                                        alt="" style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                            </div>

                            <div className={styles['grid-main']}>
                                <div className={styles['grid-item']}>
                                    <img src="../images/09.jpg"
                                        onClick={() => {
                                            setLinkImg("../images/09.jpg");
                                            setShowImgModal(true);
                                        }}
                                        alt="" style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                                <div className={styles['grid-item']}>
                                    <img src="../images/10.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/10.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                            </div>

                            <div className={styles['grid-main']}>
                                <div className={styles['grid-item']}>
                                    <img src="../images/11.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/11.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                            </div>

                            <div className={styles['grid-main']}>
                                <div className={styles['grid-item']}>
                                    <img src="../images/12.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/12.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                                <div className={styles['grid-item']}>
                                    <img src="../images/13.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/13.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
                                <div className={styles['grid-item']}>
                                    <img src="../images/14.jpg" alt=""
                                        onClick={() => {
                                            setLinkImg("../images/14.jpg");
                                            setShowImgModal(true);
                                        }}
                                        style={{ objectFit: "contain", maxWidth: "100%", maxHeight: "100%" }} />
                                </div>
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