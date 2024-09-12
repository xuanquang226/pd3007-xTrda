import styles from "../styles/sidebar.module.css";
export default function sidebar() {

    return (
        <div className={styles.sidebar}>
            <div className={styles.logo}>Logo</div>
            <nav className={styles.nav}>
                <ul>
                    <li><a href="/trang/home">HOME</a></li>
                    <li><a href="/trang/digital">DIGITAL</a></li>
                    <li><a href="/trang/watercolor">WaterColor</a></li>
                    <li><a href="/trang/comic">Comic</a></li>
                    <li><a href="/trang/sketch">Sketch</a></li>
                    <li><a href="/trang/printed-art">PrintedArt</a></li>
                    <li><a href="/trang/contact-me">ContactMe</a></li>
                </ul>
            </nav>
        </div>
    );
}