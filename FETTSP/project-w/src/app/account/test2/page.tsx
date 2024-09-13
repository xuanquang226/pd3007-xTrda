'use client'
import { useEffect, useState } from "react";
import Test from "../test/page";
import CreateProduct from "@/components/create-product";
export default function () {
    const [name, setName] = useState<string>('');
    // const [age, setAge] = useState<number>(0);
    const [classmate, setClassMate] = useState<string>('');
    const [location, setLocation] = useState<string>('');

    useEffect(() => {
        const form = document.getElementById('form-student') as HTMLFormElement | null;
        if (!form) {
            console.log("Form not found");
            return;
        }
        const handleSubmit = (event: Event) => {
            event.preventDefault();
            const formData = new FormData(form);
            setName(formData.get('name') as string);
            // setAge(formData.get('age') as number);
            setClassMate(formData.get('classmate') as string);
            setLocation(formData.get('location') as string);
        }
        form.addEventListener('submit', handleSubmit);
        return () => {
            form.removeEventListener('submit', handleSubmit);
        }
    }, []);


    return (
        <>
            <div>
                <form id="form-student">
                    <label>Name</label><input type="text" id="nameForm" name="name" />
                    {/* <label>Age</label><input type="text" id="ageForm" name="age" /> */}
                    <label>Classmate</label><input type="text" id="classForm" name="classmate" />
                    <label>Location</label><input type="text" id="locationForm" name="location" />
                    <input type="submit" value="Submit" />
                </form>
            </div>
            <Test
                name={name}
                // age={age}
                classMate={classmate}
                location={location}
            ></Test>
            <div className="container">
                <div className="row">
                    <div className="col-md-2 bg-success text-white">
                        Nội dung ở đây
                    </div>
                    <div className="col-md-10 bg-info text-white">
                        Nội dung khác ở đây
                    </div>
                </div>
            </div>
            <CreateProduct></CreateProduct>
        </>
    )
}