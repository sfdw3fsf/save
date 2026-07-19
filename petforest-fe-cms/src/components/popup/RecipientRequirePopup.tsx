import { Modal } from 'antd';
import clsx from 'clsx';

export const recipientRequirePopup = (message: string = '항목을 선택해 주세요.', className?: string, onOk?: () => void) => {
  Modal.info({
    title: <div className={clsx('text-center mb-3 mt-2.5 whitespace-pre-line', className)}>{message}</div>,
    icon: null,
    centered: true,
    maskClosable: true,
    onCancel: onOk,
    okButtonProps: { block: true, className: 'h-[54px] !bg-primary-1000 hover:!bg-primary-1000' },
    onOk: onOk,
    zIndex: 9999,
  });
};
