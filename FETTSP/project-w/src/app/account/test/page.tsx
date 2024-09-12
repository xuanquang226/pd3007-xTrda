'use client'
import Image from "next/image";
import { useEffect, useState } from "react";

interface Student {
    name: string;
    // age: number;
    classMate: string;
    location: string;
}


export default function Test(student: Student) {
    const { name, classMate, location } = student;

    useEffect(() => {
        const form = document.getElementById('my-form') as HTMLFormElement | null;
        if (!form) {
            console.error('Form element not found');
            return;
        }
        const handleSubmit = (event: Event) => {
            event.preventDefault();
            const formData = new FormData(form);
            const data: any = {};
            formData.forEach((value, key) => {
                data[key] = value;
            });

            fetch("http://localhost:8082/book", {
                method: 'POST',
                headers: { 'Content-type': 'application/json' },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Succeed ', data);
                    clearForm();
                })
                .catch(error => console.log('Error ', error));
        }
        form.addEventListener('submit', handleSubmit);
        clearForm();
        return () => {
            form.removeEventListener('submit', handleSubmit);
        }
    }, []);

    const clearForm = () => {
        const fieldNameForm = document.getElementById('nameForm') as HTMLInputElement | null;
        if (fieldNameForm) fieldNameForm.value = '';

        const fieldAuthor = document.getElementById('authorForm') as HTMLInputElement | null;
        if (fieldAuthor) fieldAuthor.value = '';
    };

    const [text, setText] = useState('');
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target) setText(event.target.value);
    }
    return (
        <>
            <div>
                <p>Hôm nay là ngày 1/8/2024, tôi đang ghi đoạn mô tả này vào lúc 11:36 sáng. Trời thật là nóng và tôi cần phải uống nước nhiều hơn. <br></br>
                    Nếu không thì sẽ khó chịu trong người.
                    Do vậy tôi đã quyết định đặt một ly nước mía uống cho đã cái nư.
                </p>

                {/* <Image src="/images/hinh.jpg" alt="hinh" width={500} height={300}></Image> */}

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name of Product</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Iphone 15</td>
                            <td>3</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Iphone 15 pro</td>
                            <td>2</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>Iphone 15 pro max</td>
                            <td>5</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <form id="my-form">
                    <label>Name:</label>
                    <input type="text" name="name" id="nameForm" /> <br />
                    <label>Author:</label>
                    <input type="text" name="author" id="authorForm" /><br />
                    <input type="submit" value="Submit" />
                </form>
            </div>

            <div>
                <input type="text" value={text} onChange={handleChange} />
                <p>Value : {text}</p>
            </div>

            <div>
                <ul>
                    <li>Name: {name}</li>
                    {/* <li>Age: {student.age}</li> */}
                    <li>Classmate: {classMate}</li>
                    <li>Location: {location}</li>
                </ul>
            </div>
        </>
    )
}