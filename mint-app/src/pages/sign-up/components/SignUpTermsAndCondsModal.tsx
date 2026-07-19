import Modal, { ModalProps } from '@/components/core/modal/Modal';
import { TermDTO } from '@/data/term/dto/term.dto';
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react';

export type SignUpTermsAndCondsModalRef = {
  setOpen: (open: boolean) => void;
};

type SignUpTermsAndCondsModalProps = Pick<ModalProps, 'header'> & { items?: TermDTO[] };

const SignUpTermsAndCondsModal = forwardRef<SignUpTermsAndCondsModalRef, SignUpTermsAndCondsModalProps>(
  ({ header, items = [] }: SignUpTermsAndCondsModalProps, ref: ForwardedRef<SignUpTermsAndCondsModalRef | null>): JSX.Element => {
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

SignUpTermsAndCondsModal.displayName = 'SignUpTermsAndCondsModal';

export default SignUpTermsAndCondsModal;
