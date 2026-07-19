import Modal, { ModalProps } from '@/components/core/modal/Modal';
import { PrivacyDTO } from '@/data/privacy/dto/privacy.dto';
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react';

export type SignUpPrivaciesModalRef = {
  setOpen: (open: boolean) => void;
};

type SignUpPrivaciesModalProps = Pick<ModalProps, 'header'> & { items?: PrivacyDTO[] };

const SignUpPrivaciesModal = forwardRef<SignUpPrivaciesModalRef, SignUpPrivaciesModalProps>(
  ({ header, items = [] }: SignUpPrivaciesModalProps, ref: ForwardedRef<SignUpPrivaciesModalRef | null>): JSX.Element => {
    const [open, setOpen] = useState<boolean>(false);

    useImperativeHandle(ref, () => ({ setOpen }));

    return (
      <Modal
        open={open}
        onClose={() => setOpen(false)}
        header={header}
        body={
          <div className='flex flex-col w-full gap-4 items-center justify-start'>
            {items.map(({ name, description }, index) => (
              <div key={index} className='flex flex-col w-full gap-2 text-left text-md max-md:text-sm'>
                <div className='font-medium text-comp-text-default'>{name}</div>
                <div dangerouslySetInnerHTML={{ __html: description }} />
              </div>
            ))}
          </div>
        }
      />
    );
  },
);

SignUpPrivaciesModal.displayName = 'SignUpPrivaciesModal';

export default SignUpPrivaciesModal;
