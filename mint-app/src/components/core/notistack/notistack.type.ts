import { CustomContentProps } from 'notistack';
import { ReactNode } from 'react';

export interface CustomNotiStackProps extends CustomContentProps {
  id: string;
  allowDownload: boolean;
  anchorOrigin: {
    horizontal: 'left' | 'center' | 'right';
    vertical: 'top' | 'bottom';
  };
  hideIconVariant: boolean;
  persist: boolean;
  message: string;
  action: ReactNode;
  autoHideDuration: number;
}
