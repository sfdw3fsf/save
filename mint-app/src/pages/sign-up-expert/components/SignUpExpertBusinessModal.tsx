import Modal from '@/components/core/modal/Modal';
import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import { advisoryFieldSchema, businessFieldSchema } from '@/utils/schemas/sign-up.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useMemo, useRef, useState } from 'react';
import { FieldValues, UseControllerProps, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { getNumberOfSelectedOptions } from '../helpers/sign-up-expert.helper';
import SignUpExpertAdvisoryFieldForm from './SignUpExpertAdvisoryFieldForm';
import SignUpExpertBusinessFieldForm from './SignUpExpertBusinessFieldForm';
import SignUpExpertBusinessModalTitle from './SignUpExpertBusinessModalTitle';

const TOTAL_STEPS = 2;

const BUSINESS_FIELD_FORM_ID = 'business-field-form';
const MAIN_ADVISORY_FORM_ID = 'main-advisory-form';

export type BusinessInput = Record<string, number | number[] | string>;
export type BusinessCombineInput = { businessFieldIds: BusinessInput; advisoryFieldIds: BusinessInput };

export type SignUpExpertBusinessFormProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'control'> & {
  id: string;
  className?: string;
  onSubmit?: () => void;
  baseData: FieldCategoryDTO[];
  errorMessage?: { title: string; detail: string };
};

export type SignUpExpertBusinessModalRef = {
  setOpen: (open: boolean) => void;
};

type SignUpExpertBusinessModalProps = {
  onSubmit: (data: BusinessCombineInput) => void;
  defaultValues?: BusinessCombineInput;
  businessFieldCategories?: FieldCategoryDTO[];
  advisoryFieldCategories?: FieldCategoryDTO[];
};

const SignUpExpertBusinessModal = forwardRef<SignUpExpertBusinessModalRef, SignUpExpertBusinessModalProps>(
  (
    { onSubmit, defaultValues, businessFieldCategories = [], advisoryFieldCategories = [] }: SignUpExpertBusinessModalProps,
    ref: ForwardedRef<SignUpExpertBusinessModalRef | null>,
  ): JSX.Element => {
    const bodyRef = useRef<HTMLDivElement | null>(null);

    const { t: tCommon } = useTranslation('common');
    const { t: tForm } = useTranslation('form');

    const [open, setOpen] = useState<boolean>(false);
    const [currentStep, setCurrentStep] = useState<number>(1);
    const [errorMessage, setErrorMessage] = useState<{ title: string; detail: string }>();

    useImperativeHandle(ref, () => ({ setOpen }));

    // BUSINESS FIELD FORM
    const businessFieldObjectSchema = useMemo(() => businessFieldSchema(businessFieldCategories), [businessFieldCategories]);
    const businessFieldMethods = useForm({
      resolver: yupResolver(businessFieldObjectSchema),
      defaultValues: businessFieldObjectSchema.getDefault(),
    });

    // MAIN ADVISORY FORM
    const advisoryFieldObjectSchema = useMemo(() => advisoryFieldSchema(advisoryFieldCategories), [advisoryFieldCategories]);
    const advisoryFieldMethods = useForm({
      resolver: yupResolver(advisoryFieldObjectSchema),
      defaultValues: advisoryFieldObjectSchema.getDefault(),
    });

    const titleProps = useMemo(() => {
      const shared = { currentStep: currentStep, totalSteps: TOTAL_STEPS };
      switch (currentStep) {
        case 1:
          return {
            ...shared,
            title: tCommon('sign_up.business_area'),
            description: tCommon('sign_up.please_select_business_area'),
          };
        case 2:
          return {
            ...shared,
            title: tCommon('sign_up.main_advisory_areas'),
            description: tCommon('sign_up.please_select_advisory_area'),
          };
        default:
          return;
      }
    }, [currentStep, tCommon]);

    const handleClose = () => {
      setOpen(false);
    };

    useEffect(() => {
      if (open) return;

      businessFieldMethods.reset(defaultValues?.businessFieldIds);
      advisoryFieldMethods.reset(defaultValues?.advisoryFieldIds);
      setErrorMessage(undefined);

      setCurrentStep(1);
    }, [businessFieldCategories, businessFieldMethods, defaultValues, advisoryFieldCategories, advisoryFieldMethods, open]);

    useEffect(() => {
      bodyRef.current?.scrollTo({ top: 0, behavior: 'smooth' });
    }, [currentStep]);

    useEffect(() => {
      if (errorMessage && bodyRef.current) {
        bodyRef.current.scrollTo({ top: bodyRef.current.scrollHeight, behavior: 'smooth' });
      }
    }, [errorMessage]);

    return (
      <Modal
        fullHeight
        open={open}
        onClose={handleClose}
        header={titleProps ? <SignUpExpertBusinessModalTitle {...titleProps} /> : undefined}
        buttons={[
          { children: tCommon('component.cancel'), onClick: handleClose },
          {
            className: currentStep === 1 ? undefined : 'hidden',
            children: tCommon('component.next'),
            variant: 'primary',
            form: BUSINESS_FIELD_FORM_ID,
          },
          {
            className: currentStep === 2 ? undefined : 'hidden',
            children: tCommon('component.previous'),
            onClick: () => setCurrentStep((prev) => prev - 1),
          },
          {
            className: currentStep === 2 ? undefined : 'hidden',
            children: tCommon('component.save'),
            variant: 'primary',
            form: MAIN_ADVISORY_FORM_ID,
          },
        ]}
        bodyRef={bodyRef}
        body={
          <>
            <SignUpExpertBusinessFieldForm
              className={currentStep === 1 ? undefined : 'hidden'}
              control={businessFieldMethods.control}
              id={BUSINESS_FIELD_FORM_ID}
              baseData={businessFieldCategories}
              errorMessage={errorMessage}
              onSubmit={businessFieldMethods.handleSubmit((data: BusinessInput) => {
                let msg, optNum;

                optNum = getNumberOfSelectedOptions(data, 'field');
                if (optNum != 2) {
                  msg = { title: tCommon('sign_up.select'), detail: tForm('required.first_business_field') };
                  // businessFieldCategories.forEach((item) => {
                  //   businessFieldMethods.setError(`field${item.id}` as any, { type: 'length' });
                  // });
                }

                optNum = getNumberOfSelectedOptions(data, 'arrayField');
                if (optNum > 5) {
                  if (!msg) msg = { title: tCommon('sign_up.selection_error'), detail: tForm('verify.select_max_5') };
                  // businessFieldCategories.forEach((item) => {
                  //   businessFieldMethods.setError(`arrayField${item.id}` as any, { type: 'length' });
                  // });
                }

                setErrorMessage(msg);
                if (msg) return;

                setCurrentStep((prev) => prev + 1);
              })}
            />
            <SignUpExpertAdvisoryFieldForm
              className={currentStep === 2 ? undefined : 'hidden'}
              control={advisoryFieldMethods.control}
              id={MAIN_ADVISORY_FORM_ID}
              baseData={advisoryFieldCategories}
              errorMessage={errorMessage}
              onSubmit={advisoryFieldMethods.handleSubmit((data: BusinessInput) => {
                let msg;
                const optNum = getNumberOfSelectedOptions(data, 'advisoryField');

                if (optNum < 1) {
                  msg = { title: tCommon('sign_up.select'), detail: tForm('required.main_advisory') };
                  // advisoryFieldCategories.forEach(({ id }) => {
                  //   advisoryFieldMethods.setError(`advisoryField${id}` as any, { type: 'length' });
                  // });
                }

                if (optNum > 5) {
                  if (!msg) msg = { title: tCommon('sign_up.selection_error'), detail: tForm('verify.select_max_5') };
                  // advisoryFieldCategories.forEach(({ id }) => {
                  //   advisoryFieldMethods.setError(`advisoryField${id}` as any, { type: 'length' });
                  // });
                }

                setErrorMessage(msg);
                if (msg) return;

                onSubmit({ businessFieldIds: businessFieldMethods.watch(), advisoryFieldIds: data });
                handleClose();
              })}
            />
          </>
        }
      />
    );
  },
);

SignUpExpertBusinessModal.displayName = 'SignUpExpertBusinessModal';

export default SignUpExpertBusinessModal;
