'use client'
import { Image } from "@/type/image";
import { Product } from "@/type/product";
import { useEffect, useState } from "react";



export default function CreateProduct() {
    const [product, setProduct] = useState<Product>({
        id: 0,
        name: '',
        description: '',
        idCategory: 1,
        imageDTOs: []
    });

    const [files, setFiles] = useState<FileList | null>(null);

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target) {
            setFiles(event.target.files);
        }
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const formData = new FormData();

        if (files) {
            Array.from(files).map(file => {
                formData.append('files', file);
            })

            const images: Image[] = Array.from(files).map((file, index) => ({
                id: index,
                url: ' ',
                description: file.name,
                idProduct: 0,
            }));

            const updatedProduct = {
                ...product,
                imageDTOs: images,
            };

            formData.append('product', JSON.stringify(updatedProduct));

            try {
                const response = await fetch("http://localhost:8082/product", {
                    method: "POST",
                    // headers: {
                    //     'Content-Type': 'application/json',
                    // khong can khi gui formdata },
                    body: formData
                });

                if (response.ok) {
                    console.log("Succeed");
                } else {
                    console.error("Failed to upload image");
                }
            } catch (error) {
                console.error("Error: ", error);
            }
        }
    };

    return (
        <div>
            <form id="product-form" onSubmit={handleSubmit}>
                <label>Name:</label>
                <input
                    type="text"
                    placeholder="input name"
                    onChange={(e) => setProduct({ ...product, name: e.target.value })}
                />
                <label>Description:</label>
                <input
                    type="text"
                    placeholder="input description"
                    onChange={(e) => setProduct({ ...product, description: e.target.value })}
                />
                <label>Category id:</label>
                <input
                    type="number"
                    placeholder="input idCategory"
                    onChange={(e) => setProduct({ ...product, idCategory: Number(e.target.value) })}
                />

                <input type="file" multiple onChange={handleFileChange} />
                <input type="submit" value="Submit" />
            </form>
        </div>
    );
}
