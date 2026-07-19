import React, { useEffect, useState } from 'react';

type ModalDialogProps = {
  children: React.ReactNode;
  visible: boolean;
  onClose: (value: boolean) => void;
};

const ModalDialog = ({ children, onClose, visible }: ModalDialogProps) => {
  const [show, setShow] = useState(visible);

  useEffect(() => {
    if (visible) {
      setShow(true);
    } else {
      setTimeout(() => setShow(false), 300); // Duration of the fade-out animation
    }
  }, [visible]);

  if (!show) return null;

  return (
    <div
      className={`relative z-[20] ${visible ? 'opacity-100' : 'opacity-0'} transition-opacity duration-300`}
      aria-labelledby='modal-title'
      role='dialog'
      aria-modal='true'>
      <div
        className='fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity duration-300'
        aria-hidden='true'
        onClick={() => onClose(false)}></div>
      <div
        className={`fixed inset-0 z-[10] bg-white w-fit h-fit mx-auto my-auto p-2 rounded-md transition-transform duration-300 ${visible ? 'scale-100' : 'scale-90'}`}>
        {children}
      </div>
    </div>
  );
};

export default ModalDialog;
