'use client'
import { useRouter, useSearchParams } from "next/navigation";
import styles from "./page.module.css";
import { Button, Form, InputGroup } from "react-bootstrap";
import { useCallback, useEffect, useState } from "react";
import Account from "@/type/account";
import { notifyError, notifySuccess } from "@/utils/notify";
import { ToastContainer } from "react-toastify";
export default function ResetPassword() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
    const router = useRouter();
    const searchParams = useSearchParams();
    const [token, setToken] = useState<string>('');
    const [idAccount, setIdAccount] = useState<number>(0);
    const [account, setAccount] = useState<Account>({
        id: 0,
        userName: '',
        password: '',
        accountType: '',
        idCustomer: 0,
        roleAccountList: [],
        status: '',
    });
    useEffect(() => {
        setToken(searchParams.get('token') ?? '');
        setIdAccount(Number(searchParams.get('idAccount')) ?? 0);
        setAccount({ ...account, id: idAccount });
    }, [idAccount]);

    const [isConfirmPassWord, setIsConfirmPassWord] = useState<boolean>(true);
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const handleConfirmPassWord = (event: React.FocusEvent<HTMLInputElement>) => {
        let value = event.target.value;
        if (value == account.password || account.password == '') {
            setIsConfirmPassWord(true);
        } else {
            setIsConfirmPassWord(false);
        }
    };

    const [isValidPassWord, setIsValidPassWord] = useState<boolean>(true);
    const handlePassWord = (event: React.FocusEvent<HTMLInputElement>) => {
        let password: string = event.target.value;

        if (password.length < 8) {
            setIsValidPassWord(false);
        } else {
            for (let i = 0; i < password.length; i++) {
                if (password.charAt(i) === '@' || password.charAt(i) === '.' || password.charAt(i) === '!'
                    || password.charAt(i) === '#' || password.charAt(i) === ',') {
                    setIsValidPassWord(true);
                    return;
                }
            }
            setIsValidPassWord(false);
        }
    };
    const [isSeePassWord, setIsSeePassWord] = useState<boolean>(false);
    const showPassWord = () => {
        if (account.password.length === 0) {
            setIsSeePassWord(false);
            return;
        }
        if (isSeePassWord === true) {
            setIsSeePassWord(false);
        } else {
            setIsSeePassWord(true);
        }
    };

    const handleSubmit = useCallback(async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (idAccount === 0) {
            router.push('/');
            return;
        }
        if (isValidPassWord == true && isConfirmPassWord == true) {
            const response = await fetch(`https://${url}/api/account/update`, {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json',
                    Authorization: token,
                },
                body: JSON.stringify(account),
            });
            if (response.ok) {
                notifySuccess("Thay đổi mật khẩu thành công");
                await delay(700);
                router.push('/sign-in');
            } else {
                notifyError("Hết hạn thay đổi mật khẩu");
                await delay(700);
                router.push('/sign-in')
            }
        } else {
            notifyError("Kiểm tra các trường nhập");
        }
    }, [account, isValidPassWord, isConfirmPassWord]);

    return (
        <div className={`container ${styles.customContainer}`}>
            <ToastContainer></ToastContainer>
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <Form id="form-id" onSubmit={handleSubmit}>
                            <div className={`${styles.formBody} col-lg-8 col-12`}>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>New password</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type={isSeePassWord ? "text" : "password"}
                                            placeholder="Input your password"
                                            value={account.password}
                                            onChange={(e) => { setAccount({ ...account, password: e.target.value }) }}
                                            onBlur={handlePassWord}
                                        ></Form.Control>
                                        <Button onClick={showPassWord} style={{ backgroundColor: "transparent", border: "none" }}>
                                            <img alt="" src="/images/view.png" />
                                        </Button>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: isValidPassWord ? "hidden" : "visible" }}>
                                        Mật khẩu cần dài hơn 8 ký tự và có một trong các ký tự sau &quot;@&quot; - &quot;.&quot; - &quot;!&quot; - &quot;#&quot; - &quot;,&quot;
                                    </Form.Text>
                                </Form.Group>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Confirm password</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type={isSeePassWord ? "text" : "password"}
                                            placeholder="Input your password"
                                            value={confirmPassword}
                                            onChange={(e) => { setConfirmPassword(e.target.value) }}
                                            onBlur={handleConfirmPassWord}
                                        ></Form.Control>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: isConfirmPassWord ? 'hidden' : 'visible' }} >Bạn cần nhập đúng mật khẩu ở trên</Form.Text>
                                </Form.Group>
                            </div>
                            <div className="form-footer">
                                <Button variant="primary" type="submit">Sign up</Button>
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    )
}

function delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}