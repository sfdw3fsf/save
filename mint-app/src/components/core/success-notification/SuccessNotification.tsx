import { enqueueSnackbar } from 'notistack';

export type SuccessNotificationProps = {
  label?: string;
  content?: string;
  bottomSection?: React.ReactNode;
};
export const SuccessNotification = ({ label, content, bottomSection }: SuccessNotificationProps) => {
  return (
    <div className='flex flex-col items-start gap-2'>
      {!!label && <div className='text-sm font-medium text-black'>{label}</div>}
      {!!content && <div className='text-sm text-slate-500'>{content}</div>}
      {!!bottomSection && bottomSection}
    </div>
  );
};
export const SuccessToast = (props: SuccessNotificationProps) => {
  const { label, content, bottomSection } = props;
  return enqueueSnackbar(<SuccessNotification label={label} content={content} bottomSection={bottomSection} />, {
    variant: 'success',
    anchorOrigin: {
      vertical: 'top',
      horizontal: 'right',
    },
    autoHideDuration: 3000,
  });
};
