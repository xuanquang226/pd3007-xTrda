'use client'
import { useEffect, useState } from "react";

interface Image {
    id: number;
    url: string;
    description: string;
    idProduct: number;
}
interface Product {
    id: number;
    name: string;
    description: string;
    idCategory: number;
    imageDTOs: Image[];
}

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

        if (files) {
            const images: Image[] = Array.from(files).map((file, index) => ({
                id: index,
                url: URL.createObjectURL(file),
                description: '',
                idProduct: 0,
            }));

            const updatedProduct = {
                ...product,
                imageDTOs: images,
            };

            try {
                const response = await fetch("http://localhost:8082/product", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(updatedProduct)
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
