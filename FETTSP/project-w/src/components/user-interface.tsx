import styles from '@/styles/user-interface.module.css';
import Customer from '@/type/customer';
import Link from 'next/link';
import { useCallback, useEffect, useState } from 'react';
import { removeToken } from '@/utils/token-utils';
interface Props {
    info: Customer;
    isPopupUserVisible: boolean;
}

export default function User(props: Props) {
    // Set info customer
    const [customer, setCustomer] = useState<Customer>({
        id: 0,
        name: '',
        mail: '',
        location: '',
        phone: '',
        idAccount: 0
    });
    const handleCustomer = useCallback(() => {
        setCustomer(props.info);
    }, [props]);

    useEffect(() => {
        handleCustomer();
    }, [handleCustomer]);

    //Handle logout
    const handleLogout = () => {
        removeToken();
        window.location.reload();
    }

    return (
        <div className='wrapper'>
            <div className={styles['user-container']} style={{ visibility: props.isPopupUserVisible ? 'visible' : 'hidden' }}>
                <div className={styles['user-info']}>
                    <Link href="#">
                        <img src="/images/user.png" alt="" />
                    </Link>
                    <p>{customer.mail}</p>
                </div>
                <div className={styles['user-functions']}>
                    <Link href="#" className={styles['custom-link']}>Quản lý tài khoản</Link>
                    <Link href="/list-order" className={styles['custom-link']}>Đơn hàng</Link>
                    <Link href="/" className={styles['custom-link']} onClick={handleLogout}>Đăng xuất</Link>
                </div>
            </div>
        </div>
    );
}