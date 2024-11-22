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
                    <div className="img-wrapper" style={{ display: 'flex', justifyContent: 'center' }}>
                        <img src={linkImg} style={{ width: "750px", height: "750px" }} />
                    </div>
                </Modal.Body>
            </Modal>
        </div>
    );
}