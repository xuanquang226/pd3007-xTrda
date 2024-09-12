'use client'

import { useRouter } from "next/navigation";

const User = () => {
    const useRoute = useRouter()
    const handleBtn = () => {
        useRoute.push("/")
    }

    return (
        <div>
            <h4>Đây là trang user</h4>
            <div>
                <button onClick={() => handleBtn()}>Back Home</button>
            </div>
        </div>
    )
}

export default User;