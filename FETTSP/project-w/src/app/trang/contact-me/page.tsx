'use client';
import Link from "next/link";
import styles from "./page.module.css";
import React, { FormEvent, useEffect, useState } from "react";
import { Mail } from "@/type/mail";
import { Button, Form, InputGroup } from "react-bootstrap";
import InputGroupText from "react-bootstrap/esm/InputGroupText";
export default function contactMe() {
    const [mail, setMail] = useState<Mail>({
        id: 0,
        name: '',
        email: '',
        phone: '',
        subject: '',
        body: ''
    });

    const [errors, setErrors] = useState({
        name: false,
        email: false,
        body: false,
        phone: false
    });

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const fullEmail: string = `${mail.email}@gmail.com`;
        const updatedMail: Mail = {
            ...mail,
            email: fullEmail
        }
        checkFieldInput(mail);
        if (errors.email === false && errors.body === false && errors.phone === false) {
            const formData = new FormData();
            formData.append('mail', JSON.stringify(updatedMail));
            try {
                const response = await fetch("http://localhost:8082/mail", {
                    method: 'POST',
                    body: formData
                });
                if (response.ok) {
                    console.log("Send mail succeed");

                } else {
                    console.log("Send mail error");
                }
            } catch (error) {
                console.log("Send mail error", error);
            }
            clearForm();
        } else {
            console.log("Nhap day du du lieu can thiet");
        }
    };

    const checkFieldInput = (mail: Mail) => {
        let newErrors = { ...errors };
        if (mail.email.length === 0) {
            newErrors.email = true;
        } else {
            newErrors.email = false;
        }

        if (mail.name.length === 0) {
            newErrors.name = true;
        } else {
            newErrors.name = false;
        }

        if (mail.phone.length === 0) {
            newErrors.phone = true;
        } else {
            newErrors.phone = false;
        }
        if (mail.body.length === 0) {
            newErrors.body = true;
        } else {
            newErrors.body = false;
        }
        setErrors(newErrors);
    };

    const clearForm = () => {
        // const inputs = document.getElementById('form-id')?.getElementsByTagName('input');
        // for (let i = 0; inputs?.length; i++) {
        //     inputs[i].value = '';
        // }

        // const form = document.getElementById('form-id') as HTMLFormElement | null;
        // if (form) form.reset();
        setMail({
            id: 0,
            name: '',
            email: '',
            phone: '',
            subject: '',
            body: ''
        })
    }

    const handlePhoneChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;

        if (/^[0-9]*$/.test(value) || value === '') {
            setMail({ ...mail, phone: value });
        }
    }

    useEffect(() => {


    }, []);

    return (
        <div className={`container ${styles.customContainer}`}>
            <div className={styles['site-wrapper']}>
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <Form id="form-id" className="" onSubmit={handleSubmit}>
                            <div className="form-header" style={{ width: "50%" }}>
                                <Form.Group className="mb-3" controlId="formName">
                                    <Form.Label>Name customer</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Input your name"
                                        value={mail.name}
                                        onChange={(e) => setMail({ ...mail, name: e.target.value })}
                                    ></Form.Control>
                                </Form.Group>
                                <Form.Text className="text-danger" style={{ visibility: errors.name ? "visible" : "hidden" }}>Không được để trống</Form.Text>
                                <Form.Group className="mb-3">
                                    <Form.Label>Email</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Input your mail"
                                            value={mail.email}
                                            onChange={(e) => setMail({ ...mail, email: e.target.value })}
                                        ></Form.Control>
                                        <InputGroupText>@gmail.com</InputGroupText>
                                    </InputGroup>
                                </Form.Group>
                                <Form.Text className="text-danger" style={{ visibility: errors.email ? "visible" : "hidden" }}>Không được để trống</Form.Text>
                                <Form.Group className="mb-3">
                                    <Form.Label>Phone number</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Input your phone number"
                                        value={mail.phone}
                                        onChange={handlePhoneChange}
                                    ></Form.Control>
                                </Form.Group>
                                <Form.Text className="text-danger" style={{ visibility: errors.phone ? "visible" : "hidden" }}>Không được để trống</Form.Text>
                                <Form.Group className="mb-3">
                                    <Form.Label>Subject</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Input your subject"
                                        value={mail.subject}
                                        onChange={(e) => setMail({ ...mail, subject: e.target.value })}
                                    ></Form.Control>
                                </Form.Group>
                            </div>
                            <div className="form-body">
                                <Form.Group className="mb-3" controlId="formBody">
                                    <Form.Label>Message Body</Form.Label>
                                    <Form.Control
                                        as="textarea"
                                        placeholder="Enter your message"
                                        value={mail.body}
                                        onChange={(e) => setMail({ ...mail, body: e.target.value })}
                                        style={{ height: '25vh', resize: "none" }}
                                    />
                                </Form.Group>
                                <Form.Text className="text-danger" style={{ visibility: errors.body ? "visible" : "hidden" }}>Không được để trống</Form.Text>
                            </div>
                            <div className="form-footer">
                                <Button
                                    type="submit"

                                >Gửi</Button>
                            </div>
                        </Form>
                    </div>
                    <div className={styles['site-contact-social']}>
                        <Link href="https://www.facebook.com/daumummim.tlc">
                            <img src="../images/instagram.png" alt="Contact" style={{ width: '30px', height: '30px' }} />
                        </Link>
                        <Link href="https://www.facebook.com/daumummim.tlc">
                            <img src="../images/tiktok.png" alt="Contact" style={{ width: '30px', height: '30px' }} />
                        </Link>
                        <Link href="https://www.facebook.com/daumummim.tlc">
                            <img src="../images/facebook.png" alt="Contact" style={{ width: '30px', height: '30px' }} />
                        </Link>

                    </div>
                </div>
            </div>
        </div>
    )
}