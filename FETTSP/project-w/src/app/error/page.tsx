'use client'
import { useParams, useSearchParams } from "next/navigation"

export default function Error() {

    const searchParams = useSearchParams();
    const errorMessage = searchParams.get('errorMessage');


    return (
        <div>
            <p>{errorMessage}</p>
        </div>
    )
}