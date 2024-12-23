'use client';
import { useCallback, useEffect, useState } from 'react';
import styles from './page.module.css';
import { Order } from '@/type/order';
import fetchWithToken from '@/utils/fetchWithToken';
import { OrderLine } from '@/type/order-line';
import Link from 'next/link';
import Image from 'next/image';
import { Product } from '@/type/product';
import { Button } from 'react-bootstrap';
export default function ListOrder() {
    const url = process.env.NEXT_PUBLIC_API_URL;
    // const url = 'localhost:8082';
    // const url2 = 'localhost:3000';
    const [page, setPage] = useState<number>(0);
    const [totalPage, setTotalPage] = useState<number>(0);

    const autoRetry = false;
    const [orderList, setOrder] = useState<Order[]>([]);

    // Get order
    const getOrder = useCallback(async () => {
        const response = await fetchWithToken(`https://${url}/api/order?page=${page}&size=5`, {
            method: 'GET'
        }, autoRetry);
        if (response && response.ok) {
            const data = await response.json();
            setOrder(data.content);
            setTotalPage(data.totalPages);
        }
    }, [page]);

    useEffect(() => {
        getOrder();
    }, [getOrder]);

    const [idOrderToIdProductMap, setIdOrderToIdProductMap] = useState<Map<number, number>>(new Map());
    const [idProductList, setIdProductList] = useState<number[]>();
    const addDataOPMap = useCallback(() => {
        const map = new Map<number, number>();
        orderList.forEach(order => {
            map.set(order.id, order?.listOrderLine[0]?.idProduct);
        });
        setIdOrderToIdProductMap(map);
        setIdProductList(Array.from(map.values()));
    }, [orderList]);

    useEffect(() => {
        addDataOPMap();
    }, [addDataOPMap]);

    const [productList, setProductList] = useState<Product[]>([]);
    const getProductList = useCallback(async () => {
        const response = await fetchWithToken(`https://${url}/api/product/many`, {
            method: 'POST',
            body: JSON.stringify(idProductList),
        }, autoRetry);
        if (response && response.ok) {
            const data = await response.json();
            setProductList(data);
        }
    }, [idProductList]);

    useEffect(() => {
        getProductList();
    }, [getProductList]);

    const [idOrderToProductMap, setIdOrderToProductMap] = useState<Map<number, Product>>();
    const createDataIdOrderToProductMap = useCallback(() => {
        const idOrderToProductMap = new Map<number, Product>();
        idOrderToIdProductMap.forEach((idProduct, idOrder) => {
            const product = productList.find(product => product.id === idProduct);
            if (product) {
                idOrderToProductMap.set(idOrder, product);
            }
        });
        setIdOrderToProductMap(idOrderToProductMap);
    }, [productList]);

    useEffect(() => {
        createDataIdOrderToProductMap();
    }, [createDataIdOrderToProductMap]);


    return (
        <div className={`container ${styles.customContainer}`}>
            <div className="site-wrapper">
                <div className={styles['site-container']}>
                    <div className={styles['site-content']}>
                        {orderList.map(order => {
                            return (<div key={order.id} className={styles['order']}>
                                <div className={styles['order__header']}>
                                    <p>Mã đơn: {order.id}</p>
                                    <p>Trạng thái: {order.status}</p>
                                </div>
                                <div className={styles['order__body']}>
                                    <Link href='#'><img src={idOrderToProductMap?.get(order.id)?.imageDTOs[0].url} alt={idOrderToProductMap?.get(order.id)?.imageDTOs[0].description} width="75px" height="75px" /></Link>
                                    <div className='content-body'>
                                        <p>{idOrderToProductMap?.get(order.id)?.name}</p>
                                        <p>Tổng tiền: {order.totalPrice} đ</p>
                                    </div>
                                </div>
                                <div className='order__footer'>
                                </div>
                            </div>)
                        })}
                    </div>
                    <div className={styles['site-pagination']}>
                        <Button variant='primary' onClick={() => setPage(page - 1)} disabled={page === 0} className={styles['page-item']}>Prev</Button>
                        <Button variant='primary' onClick={() => setPage(page + 1)} disabled={page === totalPage - 1} className={styles['page-item']}>Next</Button>
                    </div>
                </div>
            </div>
        </div>
    );
}