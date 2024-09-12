import { Modal } from "react-bootstrap";

interface propsShowImgModal {
    linkImg: string;
    showImgModal: boolean;
    setShowImgModal: (v: boolean) => void;
}


export default function showImageModal(props: propsShowImgModal) {
    const { linkImg, showImgModal, setShowImgModal } = props;
    const closeImgModal = () => {
        setShowImgModal(false);
    }
    return (
        <div>
            <Modal
                show={showImgModal}
                onHide={() => closeImgModal()}
                keyboard={true}
                size='xl'
                backdrop={true}
            >
                <Modal.Header closeButton></Modal.Header>
                <Modal.Body>
                    <div className="img-wrapper">
                        <img src={linkImg} width="100%" height="100%" />
                    </div>
                </Modal.Body>
            </Modal>
        </div>
    );
}