import { Modal } from "react-bootstrap";
import styles from "@/styles/image-modal.module.css";
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
                size='lg'
                backdrop={true}
            >
                <Modal.Header closeButton></Modal.Header>
                <Modal.Body>
                    <div className={styles['img-wrapper']} style={{ display: 'flex', justifyContent: 'center' }}>
                        <img src={linkImg} />
                    </div>
                </Modal.Body>
            </Modal>
        </div>
    );
}