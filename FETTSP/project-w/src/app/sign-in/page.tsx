'use client';
import styles from "./page.module.css";
import { Button, Form, InputGroup } from "react-bootstrap";
import Account from "@/type/account";
import { useCallback, useEffect, useState } from "react";
import { setRefreshTokenToCookie, setTokenToCookie } from "@/utils/token-utils";
import TupleToken
    from "@/type/tuple-token";
import { useRouter } from "next/navigation";
import Link from "next/link";
import useUserStore from "../store/state-user";
import { notifyError, notifySuccess } from "@/utils/notify";
import { ToastContainer } from "react-toastify";

export default function SignIn() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
    const { customerStore, addCustomer } = useUserStore();
    const router = useRouter();
    const defaultAccount: Account = {
        id: 0,
        userName: '',
        password: '',
        accountType: '',
        idCustomer: 0,
        roleAccountList: []
    }
    const [account, setAccount] = useState<Account>(defaultAccount);
    const [tupleToken, setTupleToken] = useState<TupleToken>({
        accessToken: '',
        refreshToken: ''
    });

    const handleSubmitForm = useCallback(async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const response = await fetch(`https://${url}/api/account/login`, {
            method: 'GET',
            body: JSON.stringify(account),
        });
        if (response.ok) {
            const data = await response.json();
            setTupleToken(data);
        } else {
            notifyError("Đăng nhập thất bại");
        }
    }, [account]);

    const handleToken = useCallback(() => {
        if (tupleToken.accessToken !== '' || tupleToken.refreshToken !== '') {
            setTokenToCookie(tupleToken.accessToken);
            setRefreshTokenToCookie(tupleToken.refreshToken);
            router.push('/?reload=true');
        }
    }, [tupleToken]);

    useEffect(() => {
        handleToken();
    }, [handleToken]);

    const preventAccess = useCallback(() => {
        if (customerStore.id !== 0) router.push('/');
    }, [customerStore.id]);

    useEffect(() => {
        preventAccess();
    }, [preventAccess]);

    return (
        <div className={`container ${styles.customContainer}`}>
            <ToastContainer
            />
            <div className="wrapper">
                <div className="site-container">
                    <div className="site-content">
                        <Form id="form-id" onSubmit={handleSubmitForm}>
                            <div className="mb-lg-2 form-body">
                                <Form.Group>
                                    <Form.Label>User name:</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Input your username"
                                        onChange={(e) => { setAccount({ ...account, userName: e.target.value }) }}>
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Password:</Form.Label>
                                    <Form.Control
                                        type="password"
                                        placeholder="Input your password"
                                        onChange={(e) => { setAccount({ ...account, password: e.target.value }) }}>
                                    </Form.Control>
                                </Form.Group>
                            </div>
                            <div className="form-footer">
                                <Button variant="primary" type="submit">Sign in</Button>
                                <Link href="/sign-up">Sign up</Link>
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
}