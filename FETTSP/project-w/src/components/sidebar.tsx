import { useRouter } from "next/router";
import styles from "../styles/sidebar.module.css";
export default function sidebar() {

    return (
        <div className={styles.sidebar}>
            <div className={styles.logo}>Logo</div>
            <nav className={styles.nav}>
                <ul>
                    <li><a href="/">HOME</a></li>
                    <li><a href="/category/digital">DIGITAL</a></li>
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