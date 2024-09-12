'use client';
import Link from "next/link";
import styles from "./page.module.css";
import { useEffect } from "react";
export default function comic() {
    useEffect(() => {



    }, []);

    return (
        <div className={`container ${styles.customContainer}`}>
            <h1>Comic</h1>
        </div>
    )
}