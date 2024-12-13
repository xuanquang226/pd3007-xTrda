'use client'
import { Button } from 'react-bootstrap';
import Table from 'react-bootstrap/Table'
import CreateModal from './create.modal';
import { useState } from 'react';
import { ToastContainer } from 'react-toastify';
interface Books {
    books: Book[];
}
export default function Body(arrBook: Books) {
    const [book, setBook] = useState<Book | null>(null);
    const [showModalCreate, setShowModalCreate] = useState<boolean>(false);
    return (
        <>
            <div className='mb-3' style={{ display: 'flex', justifyContent: 'space-between' }}>
                <h3>Table</h3>
                <Button onClick={() => setShowModalCreate(true)}>Add New</Button>
            </div>
            <Table bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Author</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {arrBook.books.map(item => {
                        return (
                            <tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.name}</td>
                                <td>{item.author}</td>
                                <td>
                                    <Button>View</Button>
                                    <Button variant='danger' className='mx-3'>Delete</Button>
                                    <Button variant='warning' onClick={() => {
                                        setBook(item);
                                    }}>Edit</Button>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
            <CreateModal
                showModalCreate={showModalCreate}
                setShowModalCreate={setShowModalCreate}
            />
            <ToastContainer
                position="bottom-center"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="light"
            />
        </>
    );
}

