'use client';
import React, { ReactEventHandler, useCallback, useEffect, useState } from "react";
import styles from "./page.module.css";
import Customer from "@/type/customer";
import Account from "@/type/account";
import { Button, Dropdown, DropdownButton, Form, InputGroup } from "react-bootstrap";
import useUserStore from "../store/state-user";
import { useRouter } from "next/navigation";
import { notifyError, notifySuccess } from "@/utils/notify";
import { ToastContainer } from "react-toastify";

export default function SignUp() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
    const router = useRouter();
    const accountDefault = {
        id: 0,
        userName: '',
        password: '',
        accountType: '',
        idCustomer: 0,
        roleAccountList: [],
        status: 'inactive'
    };
    const customerDefault = {
        id: 0,
        name: '',
        mail: '',
        location: '',
        phone: '',
        idAccount: 0
    };
    const [account, setAccount] = useState<Account>(accountDefault);
    const [customer, setCustomer] = useState<Customer>(customerDefault);

    const clearInputField = () => {
        setAccount((oldAccount) => {
            return accountDefault;
        })
        setCustomer((oldCustomer) => {
            return customerDefault;
        });

        setConfirmPassword('');
        setReferralCode('');
    };


    useEffect(() => {
        if (account.password == '') {
            setIsConfirmPassWord(true);
        }
    }, [account.password]);


    const [isExistsUserName, setIsExistUserName] = useState<boolean>(false);
    const [lengthUserName, setLengthUserName] = useState<string>('defaultUserName');
    const handleUserName = useCallback(async () => {
        setLengthUserName(account.userName);
        if (lengthUserName.length < 8) {
            return;
        };
        const response = await fetch(`https://${url}/api/account/validate?userName=${account.userName}`, {
            method: 'GET'
        });
        if (response.ok) {
            const data = await response.json();
            const isExists = data ? true : false;
            setIsExistUserName(isExists);
        }
    }, [account.userName, lengthUserName]);

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

    const [isValidPhone, setIsValidPhone] = useState<boolean>(true);
    const handlePhone = (event: React.FocusEvent<HTMLInputElement>) => {
        let phone: string = event.target.value;

        if (parseInt(phone.charAt(0) as string) === 0 && phone.length === 10) {
            setIsValidPhone(true);
        } else {
            setIsValidPhone(false);
        }
    };



    // const [tailMail, setTailMail] = useState<string>('');
    // const handleSelect = (eventKey: string | null) => {
    //     setTailMail(eventKey ?? '');
    // };

    // const handleMail = () => {
    //     let updatedMail = customer.mail + tailMail;
    //     setCustomer({ ...customer, mail: updatedMail });
    // };


    const [isUnValidMail, setIsUnValidMail] = useState<boolean>(false);
    const [isExistsMail, setIsExistMail] = useState<boolean>(false);

    const handleMail = useCallback(async (event: React.FocusEvent<HTMLInputElement>) => {
        const mail: string = event.target.value.trim();
        const validDomains = ['gmail.com', 'outlook.com', 'icloud.com', 'yahoo.com'];

        if (!mail.includes('@')) {
            setIsUnValidMail(true);
            setIsExistMail(false);
            return;
        }

        const domain = mail.split('@')[1];
        if (!validDomains.includes(domain)) {
            setIsUnValidMail(true);
            setIsExistMail(false);
            return;
        }

        setIsUnValidMail(false);

        try {
            const response = await fetch(`https://${url}/api/customer/validate?mail=${mail}`, {
                method: 'GET',
            });

            if (response.ok) {
                const data = await response.json();
                setIsExistMail(!!data);
            } else {
                setIsExistMail(false);
            }
        } catch (error) {
            console.error("API call failed", error);
            setIsExistMail(false);
        }

    }, [customer.mail]);

    const [referralCode, setReferralCode] = useState<string>('');
    const handleReferralCode = () => {
        if (referralCode.trim() === '031297') {
            setAccount({ ...account, accountType: 'ADMIN' })
        } else {
            setAccount({ ...account, accountType: 'USER' })
        }

        if (referralCode.trim().length === 0) setAccount({ ...account, accountType: 'USER' });
    };

    const handleSubmit = useCallback(async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (isExistsUserName == false && isExistsMail == false && isConfirmPassWord == true && isUnValidMail == false && isValidPassWord == true
            && isValidPhone == true && account.userName.length !== 0 && account.password.length !== 0 && customer.name.length !== 0
            && customer.phone.length !== 0 && customer.mail.length !== 0) {

            notifySuccess("Đăng ký thành công");
            await delay(800);
            const formData = new FormData();
            formData.append('account', JSON.stringify(account));
            formData.append('customer', JSON.stringify(customer));
            router.push('/sign-in');
            const response = await fetch(`https://${url}/api/account/sign-up`, {
                method: 'POST',
                body: formData
            });
            if (response.ok) {
                clearInputField();
            }
        } else {
            notifyError("Đăng ký thất bại kiểm tra các trường nhập");
        }
    }, [lengthUserName, isExistsUserName, isExistsMail, isConfirmPassWord, isUnValidMail, isValidPassWord, isValidPhone, account, customer]);

    const { customerStore, addCustomer } = useUserStore();
    const preventAccess = useCallback(() => {
        if (customerStore.id !== 0) router.push('/');
    }, [customerStore.id]);

    useEffect(() => {
        preventAccess();
    }, [preventAccess]);

    return (
        <div className={`container ${styles.customContainer}`}>
            <ToastContainer />
            <div className="wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        <Form id="form-id" onSubmit={handleSubmit}>
                            <div className={`${styles.formBody} col-lg-8 col-12`}>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>User name</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter your username"
                                            value={account.userName}
                                            onChange={(e) => { setAccount({ ...account, userName: e.target.value }) }}
                                            onBlur={handleUserName}
                                        ></Form.Control>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: (lengthUserName.length < 8) || isExistsUserName ? "visible" : "hidden" }}>
                                        {(lengthUserName.length < 8) ? 'Tên tài khoản quá ngắn' : isExistsUserName ? 'Tài khoản đã tồn tại' : ''}
                                    </Form.Text>
                                </Form.Group>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Password</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type={isSeePassWord ? "text" : "password"}
                                            placeholder="Enter your password"
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
                                            placeholder="Enter your password"
                                            value={confirmPassword}
                                            onChange={(e) => { setConfirmPassword(e.target.value) }}
                                            onBlur={handleConfirmPassWord}
                                        ></Form.Control>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: isConfirmPassWord ? 'hidden' : 'visible' }} >Bạn cần nhập đúng mật khẩu ở trên</Form.Text>
                                </Form.Group>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Name</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter your name"
                                            value={customer.name}
                                            onChange={(e) => { setCustomer({ ...customer, name: e.target.value }) }}
                                        ></Form.Control>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: 'hidden' }} >aaaa</Form.Text>
                                </Form.Group>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Phone</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="number"
                                            placeholder="Enter your phone"
                                            value={customer.phone}
                                            onChange={(e) => { setCustomer({ ...customer, phone: e.target.value }) }}
                                            onBlur={handlePhone}
                                        ></Form.Control>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: isValidPhone ? 'hidden' : 'visible' }} >Số điện thoại không đúng định dạng 0 đầu và 10 số</Form.Text>
                                </Form.Group>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Mail</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter your mail"
                                            value={customer.mail}
                                            onChange={(e) => { setCustomer({ ...customer, mail: e.target.value }) }}
                                            onBlur={handleMail}
                                        ></Form.Control>
                                        {/* <DropdownButton
                                            variant="outline-secondary"
                                            title={tailMail}
                                            id="dropdown-basic-button"
                                            onSelect={handleSelect}
                                        >
                                            <Dropdown.Item eventKey="@gmail.com">@gmail.com</Dropdown.Item>
                                            <Dropdown.Item eventKey="@outlook.com">@outlook.com</Dropdown.Item>
                                            <Dropdown.Item eventKey="@yahoo.com">@yahoo.com</Dropdown.Item>
                                            <Dropdown.Item eventKey="@icloud.com">@icloud.com</Dropdown.Item>
                                        </DropdownButton> */}
                                    </InputGroup>
                                    <Form.Text
                                        className="text-danger"
                                        style={{ visibility: isUnValidMail || isExistsMail ? 'visible' : 'hidden' }}
                                    >
                                        {isUnValidMail
                                            ? 'Nhập đúng định dạng'
                                            : isExistsMail
                                                ? 'Đã tồn tại mail'
                                                : ''}
                                    </Form.Text>
                                </Form.Group>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Location</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter your location"
                                            value={customer.location}
                                            onChange={(e) => { setCustomer({ ...customer, location: e.target.value }) }}
                                        ></Form.Control>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: 'hidden' }} >aaaa</Form.Text>
                                </Form.Group>
                                <Form.Group className="mb-lg-2">
                                    <Form.Label>Referral code</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter your referral code"
                                            value={referralCode}
                                            onChange={(e) => { setReferralCode(e.target.value) }}
                                            onBlur={handleReferralCode}
                                        ></Form.Control>
                                    </InputGroup>
                                    <Form.Text className="text-danger" style={{ visibility: 'hidden' }} >aaaa</Form.Text>
                                </Form.Group>
                            </div>
                            <div className="form-footer">
                                <Button variant="primary" type="submit">Sign up</Button>
                            </div>
                        </Form>
                    </div>
                </div>
            </div >
        </div >
    )
}

function delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}