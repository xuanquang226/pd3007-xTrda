'use client';
import Link from "next/link";
import styles from "./page.module.css";
import React, { FormEvent, useCallback, useEffect, useState } from "react";
import { Mail } from "@/type/mail";
import { Button, Form, InputGroup } from "react-bootstrap";
import InputGroupText from "react-bootstrap/esm/InputGroupText";
import { notifyError, notifySuccess } from "@/utils/notify";
import { ToastContainer } from "react-toastify";
export default function ContactMe() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
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
        phone: false,
        subject: false,
    });

    const handleSubmit = useCallback(async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const fullEmail: string = `${mail.email}@gmail.com`;
        const updatedMail: Mail = {
            ...mail,
            email: fullEmail
        }
        // checkFieldInput(mail);
        if (errors.email === false && errors.body === false && errors.phone === false && mail.name.length !== 0
            && mail.body.length !== 0 && mail.email.length !== 0 && mail.subject.length !== 0 && mail.phone.length !== 0
        ) {
            const formData = new FormData();
            formData.append('mail', JSON.stringify(updatedMail));
            notifySuccess("Yêu cầu của bạn đã được gửi đi thành công");
            clearForm();
            try {
                const response = await fetch(`https://${url}/api/mail`, {
                    method: 'POST',
                    body: formData
                });
                if (response.ok) {
                    console.log("Send mail succeed");
                }
            } catch (error) {
                console.log("Send mail error", error);
            }
        } else {
            notifyError("Nhập đầy đủ các trường");
        }
    }, [mail, errors]);

    // const checkFieldInput = useCallback((mail: Mail) => {
    //     let newErrors = { ...errors };
    //     if (mail.email.length === 0) {
    //         newErrors.email = true;
    //     } else {
    //         newErrors.email = false;
    //     }

    //     if (mail.name.length === 0) {
    //         newErrors.name = true;
    //     } else {
    //         newErrors.name = false;
    //     }

    //     if (mail.phone.length === 0) {
    //         newErrors.phone = true;
    //     } else {
    //         newErrors.phone = false;
    //     }
    //     if (mail.body.length === 0) {
    //         newErrors.body = true;
    //     } else {
    //         newErrors.body = false;
    //     }
    //     setErrors(newErrors);
    // }, [mail, errors]);

    const clearForm = () => {
        setMail({
            id: 0,
            name: '',
            email: '',
            phone: '',
            subject: '',
            body: ''
        })
    }

    const handlePhoneChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;

        if (/^[0-9]*$/.test(value) || value === '') {
            setMail({ ...mail, phone: value });
        }
    }, [mail]);

    useEffect(() => {


    }, []);

    // Check focus event of input field
    const focusNameCustomer = useCallback(() => {
        if (mail.name.length === 0) {
            setErrors({ ...errors, name: true });
        } else {
            setErrors({ ...errors, name: false });
        }
    }, [errors, mail]);

    const focusMailCustomer = useCallback(() => {
        if (mail.email.length === 0) {
            setErrors({ ...errors, email: true });
        } else {
            setErrors({ ...errors, email: false });
        }
    }, [errors, mail]);

    const focusBodyCustomer = useCallback(() => {
        if (mail.body.length === 0) {
            setErrors({ ...errors, body: true });
        } else {
            setErrors({ ...errors, body: false });
        }
    }, [errors, mail]);

    const focusPhoneCustomer = useCallback(() => {
        if (mail.phone.length === 0) {
            setErrors({ ...errors, phone: true });
        } else {
            setErrors({ ...errors, phone: false });
        }
    }, [errors, mail]);

    const focusSubjectCustomer = useCallback(() => {
        if (mail.subject.length === 0) {
            setErrors({ ...errors, subject: true });
        } else {
            setErrors({ ...errors, subject: false });
        }
    }, [errors, mail]);



    return (
        <div className={`container ${styles.customContainer}`}>
            <ToastContainer />
            <div className={styles['site-wrapper']}>
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <Form id="form-id" className="" onSubmit={handleSubmit}>
                            <div className="form-header col-lg-6 col-12" >
                                <Form.Group className="mb-3" controlId="formName">
                                    <Form.Label>Name customer</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Input your name"
                                        value={mail.name}
                                        onChange={(e) => setMail({ ...mail, name: e.target.value })}
                                        onBlur={focusNameCustomer}
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
                                            onBlur={focusMailCustomer}
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
                                        onBlur={focusPhoneCustomer}
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
                                        onBlur={focusBodyCustomer}
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
                        <Link href="https://www.instagram.com/trada._/">
                            <img src="../images/instagram.png" alt="Contact" style={{ width: '30px', height: '30px' }} />
                        </Link>
                        <Link href="https://www.tiktok.com/@xiaotrada">
                            <img src="../images/tiktok.png" alt="Contact" style={{ width: '30px', height: '30px' }} />
                        </Link>
                        <Link href="https://www.facebook.com/tradaxiao/">
                            <img src="../images/facebook.png" alt="Contact" style={{ width: '30px', height: '30px' }} />
                        </Link>

                    </div>
                </div>
            </div>
        </div>
    )
}