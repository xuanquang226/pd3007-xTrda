'use client'
import { useEffect, useState } from "react";
import styles from "../styles/handle-img.module.css"

export default function HandleImg() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    const [files, setFile] = useState<File[]>();
    const [data, setData] = useState<string>('');
    const tempURLs: any = [];
    useEffect(() => {
        const form = document.getElementById(`form-input-file`) as HTMLFormElement;
        if (!form) console.log("form not found");
        const handleSubmit = async (event: Event) => {
            event.preventDefault();
            const formData = new FormData(form);
            setFile(formData.getAll("file") as File[]);

            try {
                const response = await fetch(`http://${url}:8080/api/images/upload2`, {
                    method: "POST",
                    body: formData
                });
                if (response.ok) {
                    const data = await response.text();
                    console.log("DATAA", data);
                    setData(data);
                } else {
                    console.error("Failed to upload image");
                }
            } catch (error) {
                console.log("error: ", error);
            }

        }
        form.addEventListener('submit', handleSubmit);
        return () => {
            form.removeEventListener('submit', handleSubmit);
        }
    }, []);
    files?.forEach((value, key) => {
        tempURLs[key] = URL.createObjectURL(value);
    });
    return (
        <div className={`container ${styles.customContainer}`}>
            <div className="form-wrapper">
                <form id="form-input-file">
                    <label>Input file</label>
                    <input type="file" name="file" accept=".jpg" multiple></input>
                    <input type="submit" value="submit" />
                </form>
            </div>

            <div className="random-content">
                <img src={data} />
                {files?.map((file) => {
                    let idFile = 0;
                    return (<h4 key={idFile + 1}>{file.name}</h4>)
                }
                )}
            </div>
        </div>
    );
}