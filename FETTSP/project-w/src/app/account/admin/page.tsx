'use client'
import { useRouter } from "next/navigation";
import Button from 'react-bootstrap/Button';
import styles from "@/styles/app.module.css"


const Admin = () => {
    const useRoute = useRouter()
    const handleBtn = () => {
        useRoute.push("/")
    }
    return (
        <div>
            <h3 className={styles.blue}>Đây là trang admin</h3>
            <div>
                <Button variant="danger">Yes</Button>
                <button onClick={() => handleBtn()}>Back home</button>
            </div>
        </div>
    )
}

export default Admin;