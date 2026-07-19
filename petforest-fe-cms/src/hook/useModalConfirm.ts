import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import { isArray } from 'lodash';
import { useState } from 'react';

type UseModalConfirmReturn = {
  isOpen: boolean | { isOpen: boolean; ids: number | number[] };
  openModal: (ids?: number | number[]) => void;
  closeModal: () => void;
  setIsOpen: (data: boolean | { isOpen: boolean; ids: number }) => void;
};

export const useModalConfirm = (): UseModalConfirmReturn => {
  const [isOpen, setIsOpen] = useState<boolean | { isOpen: boolean; ids: number | number[] }>(false);

  const openModal = (ids?: number | number[]) => {
    if (ids && isArray(ids) && ids.length === 0) {
      recipientRequirePopup();
      return;
    }

    setIsOpen(ids ? { isOpen: true, ids } : true);
  };

  const closeModal = () => {
    setIsOpen(false);
  };

  return {
    isOpen,
    openModal,
    closeModal,
    setIsOpen,
  };
};
