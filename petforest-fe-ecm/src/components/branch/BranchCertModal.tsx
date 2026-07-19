'use client';

import { BranchDetailType } from '@/utils/constant/branch/branch-detail.constant';
import { Image, Modal, ModalBody, ModalCloseButton, ModalContent, ModalOverlay } from '@chakra-ui/react';

const BranchCertModal = ({
  branchDetail,
  isOpen,
  onClose,
}: {
  branchDetail: BranchDetailType;
  isOpen: boolean;
  onClose: any;
}) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='transparent' p={0} style={{ width: '90%' }}>
        <ModalCloseButton />
        <ModalBody p={0}>
          <Image src={branchDetail.certificate} alt='' />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default BranchCertModal;
