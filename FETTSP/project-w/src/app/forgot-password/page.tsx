'use client'
import styles from "./page.module.css";
import { Button, Form, InputGroup } from "react-bootstrap";
import { FormEvent, useCallback, useEffect, useState } from "react";
import { notifyError, notifySuccess } from "@/utils/notify";
import { useRouter } from "next/navigation";
import { ToastContainer } from "react-toastify";
export default function ForgotPassword() {
    const router = useRouter();
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
    const [mail, setMail] = useState<string>('');
    const [message, setMessage] = useState<Boolean>(false);
    const handleSubmit = useCallback(async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const response = await fetch(`https://${url}/api/account/forgot-password?email=${mail}`, {
            method: 'GET',
        });
        if (response.ok) {
            const data = await response.json();
            setMessage(data);
            if (data) {
                notifySuccess('Kiểm tra mail để reset password');
                await delay(3000);
                router.push('/sign-in');
            } else {
                notifyError('Không tồn tại mail này');
            }
        }
    }, [mail]);
    return (
        <div className={`container ${styles.customContainer}`}>
            <ToastContainer />
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <Form id="form-id" onSubmit={handleSubmit}>
                            <div className={`${styles.formBody} col-lg-8 col-12`}>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Mail</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter your mail"
                                            value={mail}
                                            onChange={(e) => { setMail(e.target.value) }}
                                        ></Form.Control>
                                    </InputGroup>
                                </Form.Group>
                            </div>
                            <div className="form-footer">
                                <Button variant="primary" type="submit">Send</Button>
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
}

function delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}