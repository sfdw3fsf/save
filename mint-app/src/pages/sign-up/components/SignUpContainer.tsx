import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import PageContainer from '@/components/core/page-container/PageContainer';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import { ReactNode } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import SignUpContainerTitle, { SignUpContainerTitleProps } from './SignUpContainerTitle';

const TOTAL_STEPS = 3;

export type SignUpContainerProps = {
  onBack: () => void;
  onNext?: () => void;
  form?: string;
  children?: ReactNode;
  className?: string;
  titleProps: SignUpContainerTitleProps;
  isLoading?: boolean;
  isLastStep?: boolean;
};

const SignUpContainer = ({
  children,
  titleProps,
  onBack,
  onNext,
  form,
  className,
  isLoading = false,
  isLastStep = false,
}: SignUpContainerProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  return (
    <PageContainer withBackground title={tCommon('title.sign_up')}>
      <Card className={twMerge('md:gap-20', className)}>
        <div className='flex flex-col w-full gap-10'>
          <SignUpContainerTitle {...titleProps} totalSteps={TOTAL_STEPS} />

          {children}
        </div>

        <div className='flex w-full flex-col gap-4'>
          <Button className='w-full' form={form} onClick={onNext} variant='primary' isLoading={isLoading}>
            {isLastStep ? tCommon('sign_up.sign_up') : tCommon('sign_up.next')}
          </Button>
          <Button className='w-full' onClick={onBack} variant='grayBox' leftIcon={<ArrowLeftIcon />} sizing='sm'>
            {tCommon('sign_up.go_back')}
          </Button>
        </div>
      </Card>
    </PageContainer>
  );
};

export default SignUpContainer;
