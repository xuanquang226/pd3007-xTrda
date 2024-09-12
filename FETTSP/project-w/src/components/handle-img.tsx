'use-client'
import { useEffect, useState } from "react";
import styles from "../styles/handle-img.module.css"
import { mutate } from "swr";

export default function handleImg() {
    const [files, setFile] = useState<File[]>();
    const [data, setData] = useState<string>('');
    const tempURLs: any = [];
    useEffect(function () {
        const form = document.getElementById(`form-input-file`) as HTMLFormElement;
        if (!form) console.log("form not found");
        const handleSubmit = async (event: Event) => {
            event.preventDefault();
            const formData = new FormData(form);
            setFile(formData.getAll("file") as File[]);

            try {
                const response = await fetch("http://localhost:8082/images/upload2", {
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
                {files?.map((file) => (
                    <h4>{file.name}</h4>
                ))}
                {/* {tempURLs?.map((tempURL: any) => (
                    <img src={tempURL} width={300} height={200} style={{ display: "flex", flexDirection: "column" }} />
                ))} */}

            </div>
        </div>
    );
}