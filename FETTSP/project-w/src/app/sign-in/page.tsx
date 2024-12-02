'use client';
import classNames from "classnames";
import styles from "./page.module.css";
import { Button, Form, InputGroup } from "react-bootstrap";
import Account from "@/type/account";
import { useCallback, useEffect, useState } from "react";
import { setRefreshTokenToCookie, setTokenToCookie } from "@/utils/token-utils";
import TupleToken
    from "@/type/tuple-token";

export default function SignIn() {
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

    const handleSubmitForm = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const response = await fetch(`http://localhost:8082/account/login?username=${account.userName}&password=${account.password}`, {
            method: 'GET'
        })
        if (response.ok) {
            const data = await response.json();
            setTupleToken(data);
            alert('Dang nhap thanh cong');
        } else {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
    };

    const handleToken = useCallback(() => {
        setTokenToCookie(tupleToken.accessToken);
        setRefreshTokenToCookie(tupleToken.refreshToken);
    }, [tupleToken]);

    useEffect(() => {
        handleToken();
    }, [handleToken]);




    return (
        <div className={`container ${styles.customContainer}`}>
            <div className="wrapper">
                <div className="site-container">
                    <div className="site-content">
                        <Form id="form-id" onSubmit={handleSubmitForm}>
                            <div className="form-body">
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
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
}