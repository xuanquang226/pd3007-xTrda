import { useRouter } from "next/router";
import styles from "../styles/sidebar.module.css";
import Link from "next/link";

export default function Sidebar() {
    return (
        <div className={styles['sidebar']}>
            <div className={`${styles.logo} w-fit h-fit`}>
                <Link href="/">
                    <img src="/images/logo.png" alt="" className={`w-42 h-auto`} />
                </Link>
            </div>
            <nav className={styles.nav}>
                <ul>
                    <li><a href="/">Home</a></li>
                    <li><a href="/category/digital">Digital</a></li>
                    <li><a href="/category/water-color">WaterColor</a></li>
                    <li><a href="/category/comic">Comic</a></li>
                    <li><a href="/category/sketch">Sketch</a></li>
                    <li><a href="/category/printed-art">PrintedArt</a></li>
                    <li><a href="/contact-me">ContactMe</a></li>
                </ul>
            </nav>
        </div>
    );
}