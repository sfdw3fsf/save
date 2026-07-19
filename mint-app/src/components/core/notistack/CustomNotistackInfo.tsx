import { SnackbarContent, closeSnackbar } from 'notistack';
import React from 'react';
import Alert from '../alert/Alert';
import { CustomNotiStackProps } from './notistack.type';

interface CustomNotistackInfoProps extends CustomNotiStackProps {}

const CustomNotistackInfo = React.forwardRef<HTMLDivElement, CustomNotistackInfoProps>((props, ref) => {
  const { id, message, allowDownload, anchorOrigin, iconVariant, hideIconVariant, persist, autoHideDuration, action, ...others } =
    props;

  return (
    <SnackbarContent ref={ref} role='alert' {...others}>
      <Alert variant='toast-info' onClose={() => closeSnackbar(id)}>
        {message}
      </Alert>
    </SnackbarContent>
  );
});

CustomNotistackInfo.displayName = 'CustomNotistackInfo';

const CustomNotistackInfoMemo = React.memo(CustomNotistackInfo);

export default CustomNotistackInfoMemo;
