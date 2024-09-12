import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import { toast } from 'react-toastify';
import { mutate } from 'swr';
interface IProps {
    showModalUpdate: boolean;
    setShowModalUpdate: (v: boolean) => void;
    book: Book | null;
    setBook: (v: Book | null) => void;
}

function UpdateModal(props: IProps) {
    const { showModalUpdate, setShowModalUpdate, book, setBook } = props;

    const [id, setId] = useState<number>(0);
    const [name, setName] = useState<string>("");
    const [author, setAuthor] = useState<string>("");

    useEffect(() => {
        if (book?.id && book) {
            setId(book.id);
            setName(book.name);
            setAuthor(book.author);
            setBook(null);
        }
    }, [book]);

    const handleSubmit = async () => {

        if (!name || !author) {
            toast.error("Name or author is not empty");
            return;
        }
        const response = await fetch("http://localhost:8082/book", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json, text/plain, */*"
            },
            body: JSON.stringify({ id, name, author })
        })
        // .then((res) => res.json())
        //     .then(res => {
        //         if (res) toast.success("Save succeed !!!")
        //     })
        if (response.ok) {
            toast.success("Update succeed")
            mutate("http://localhost:8082/book");
            handleCloseModal();
        }

    }

    const handleCloseModal = () => {
        setName("");
        setAuthor("");
        setShowModalUpdate(false);
    }

    return (
        <>

            <Modal
                show={showModalUpdate}
                onHide={() => handleCloseModal()}
                backdrop="static"
                keyboard={false}
                size='lg'
            >
                <Modal.Header closeButton>
                    <Modal.Title>Add book</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" >
                            <Form.Label>Name</Form.Label>
                            <Form.Control as="textarea" rows={1} placeholder="id"
                                value={id}
                                onChange={(e) => setId(parseInt(e.target.value))}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" >
                            <Form.Label>Name</Form.Label>
                            <Form.Control as="textarea" rows={1} placeholder="name"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Author</Form.Label>
                            <Form.Control as="textarea" rows={1} placeholder='author'
                                value={author}
                                onChange={(e) => setAuthor(e.target.value)}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => handleCloseModal()}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={() => handleSubmit()}>Update</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default UpdateModal;