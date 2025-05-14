'use client';
import { useRouter } from "next/router";
import styles from "../styles/sidebar.module.css";
import Link from "next/link";
import useHButton from "@/app/store/state-hbutton";
import { useEffect, useRef, useState } from "react";
export default function Sidebar() {

    const {isClicked, setClicked} = useHButton();
    
    useEffect(() => {
        const handleResize = () => {
            if(window.innerWidth >= 768){
                setClicked(false);
            }      
        };
        handleResize();
        window.addEventListener("resize", handleResize);
        return () => window.removeEventListener("resize", handleResize);
    }, []);

    const divSidebarRef = useRef<HTMLDivElement>(null);
    const handleClickOutSide = (event: MouseEvent) => {
        if(divSidebarRef.current && divSidebarRef.current.contains(event.target as HTMLElement)){
            setClicked(false);
        }
    };
    useEffect(()=>{
        window.addEventListener('click', handleClickOutSide);
        return () => {
            window.removeEventListener('click', handleClickOutSide);
        }
    },[isClicked]);
    return (
        <div ref={divSidebarRef}
            className={`flex flex-col items-center text-sm transform transition-transform duration-200
                        md:translate-x-0 md:flex md:w-1/5 md:text-base md:static
                        bg-white z-50 fixed top-0 left-0 w-2/5 h-screen ${isClicked? 'translate-x-0' : '-translate-x-full'}`}>
            <div className={`${styles.logo} w-fit h-fit`}>
                <Link href="/">
                    <img src="/images/logo.png" alt="" className={`w-42 h-auto`} />
                </Link>
            </div>
            <nav className={`${styles.nav} w-32 shadow-md`}>
                <ul>
                    <li><a href="/category/digital">Digital</a></li>
                    <li><a href="/category/water-color">WaterColor</a></li>
                    <li><a href="/category/comic">Comic</a></li>
                    <li><a href="/category/sketch">Sketch</a></li>
                    <li><a href="/category/printed-art">PrintedArt</a></li>
                    <li><a href="/contact-me">ContactMe</a></li>
                    <li><a href="#">AboutMe</a></li>
                </ul>
            </nav>
        </div>
    );
}