'use client';
import Link from "next/link";
import styles from "./page.module.css";
import { useEffect } from "react";
export default function printedArt() {
    useEffect(() => {



    }, []);

    return (
        <div className={`container ${styles.customContainer}`}>
            <h1>printedArt</h1>
        </div>
    )
}