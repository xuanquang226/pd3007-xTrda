import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import { toast } from 'react-toastify';
import { mutate } from 'swr';
import { FormGroup } from 'react-bootstrap';
interface IProps {
    showModalCreate: boolean;
    setShowModalCreate: (v: boolean) => void;
}

export default function CreateModal(props: IProps) {
    const url = process.env.NEXT_PUBLIC_API_URL;
    const { showModalCreate, setShowModalCreate } = props;
    const [name, setName] = useState<string>("");
    const [author, setAuthor] = useState<string>("");

    const handleSubmit = () => {
        if (!name || !author) {
            toast.error("Name or author is not empty");
            return;
        }
        fetch(`http://${url}:8082/book`, {
            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json, text/plain, */*"
            },
            body: JSON.stringify({ name, author })
        })
            .then(res => res.json())
            .then(data => {
                toast.success("Add success");
                mutate(`http://${url}:8082/book`);
                handleCloseModal();
            })
    }
    const handleCloseModal = () => {
        setName("");
        setAuthor("");
        setShowModalCreate(false);
    }

    return (
        <>
            <Modal
                show={showModalCreate}
                onHide={() => handleCloseModal()}
                backdrop="static"
                keyboard={true}
                size='lg'
            >
                <Modal.Header closeButton>
                    <Modal.Title>Add new book</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className='mb-3 border border-info rounded bg-success d-inline-block w-75'>
                            <Form.Label>Name book</Form.Label>
                            <Form.Control as="textarea" rows={1} placeholder="name"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            ></Form.Control>
                        </Form.Group>
                        <FormGroup className='mb-3'>
                            <Form.Label>Name Author</Form.Label>
                            <Form.Control as="textarea" rows={1} placeholder="name author"
                                value={author}
                                onChange={(e) => setAuthor(e.target.value)}
                            ></Form.Control>
                        </FormGroup>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => handleCloseModal()}>Close</Button>
                    <Button variant="primary" onClick={() => handleSubmit()}>Save</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}