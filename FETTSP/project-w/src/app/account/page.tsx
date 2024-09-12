'use client'
import Link from "next/link";
import styles from "@/styles/app.module.css";
import Body from "@/components/body";
import { useEffect, useState } from "react";
import useSWR from "swr";
const XQ226 = () => {
    // const fetcher = (url: string) => fetch(url).then((res) => res.json());
    // const { data, error, isLoading } = useSWR(
    //     "http://localhost:8082/book",
    //     fetcher
    //     , {
    //         revalidateIfStale: false,
    //         revalidateOnFocus: false,
    //         revalidateOnReconnect: false
    //     }
    // );
    // console.log(data)
    const [data, setData] = useState<Book[] | null>(null);
    useEffect(() => {
        fetch("http://localhost:8082/book")
            .then(res => res.json())
            .then(data => setData(data));
    }, []);


    if (!data) return (<div>Loading...</div>);
    const sortedData: Book[] = data.sort((a, b) => a.id - b.id);
    return (
        <div>
            <div>
                {data?.length}
            </div>
            <ul>
                <li className={styles.red}>
                    <Link href="/account/admin" className={styles.red}>Admin</Link>
                </li>
                <li>
                    <Link href="/account/user">User</Link>
                </li>
            </ul>
            <Body
                books={sortedData}
            // books={data.sort((a: any, b: any) => b.name.localeCompare(a.name))}
            />
            {/* {Body({ books: sortedData })} */}
        </div>

    )
}

export default XQ226;

