'use client';
import '@/components/reservation/reservation.css';
import { ReservationInquiryREQ } from '@/store/reservation/request/reservation.request';
import { getReservationInquiryApi } from '@/store/reservation/reservation.api';
import { useReservationStore } from '@/store/reservation/reservation.store';
import { getErrorMessages } from '@/utils/helper/reservation.helper';
import { reservationInquirySchema } from '@/utils/schema/reservation-inquiry.schema';
import { Modal, ModalBody, ModalContent, ModalHeader, ModalOverlay } from '@chakra-ui/react';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
const ReservationPage = () => {
  const router = useRouter();

  const { setReservationId, setSelectedBranch } = useReservationStore((state) => state);

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<ReservationInquiryREQ>({
    resolver: yupResolver(reservationInquirySchema),
    defaultValues: {
      guardianName: '',
      phoneNumber: '',
    },
  });

  const [isOpenError, setIsOpenError] = useState<boolean>(false);

  const { mutate: getReservationInquiryData } = useMutation({
    mutationFn: (params: ReservationInquiryREQ) => getReservationInquiryApi(params),
    onSuccess: (response) => {
      if (!response.data.data) {
        alert('예약된 장례가 없습니다.');
      } else {
        setReservationId(response.data.data?.id);
        setSelectedBranch(response.data.data?.branch?.id);
        router.push('/reservation/confirm');
      }
    },
  });

  const onSubmit = (values: ReservationInquiryREQ) => {
    getReservationInquiryData(values);
  };

  const onError = () => {
    setIsOpenError(true);
  };

  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value.replace(/-/g, '');
    if (value.length > 4) {
      value = `${value.slice(0, 4)}-${value.slice(4)}`;
    }
    setValue('phoneNumber', value);
  };

  return (
    <div className='cont !mb-[70px] md:!mb-[417px]' id='check_reserve'>
      <div className='!mt-[55px] cont-head cont-head2 flex justify-center items-center flex-col text-center md:!mt-[80px] md:mb-[65px] '>
        <h1 className='text-black text-[27px] font-bold leading-none md:text-[33px] '>예약 정보 조회</h1>
        <a
          href='https://booking.naver.com/booking/6/bizes/563611?area=bbt'
          target='_blank'
          rel='noopener noreferrer'
          className='inline-block py-[13px] px-[17px] mt-[35px] border border-green-lime mx-auto text-center text-[13px] text-green-lime leading-none rounded-[3px]'>
          <span>네이버 예약 조회</span>
        </a>
      </div>
      <div className='lmt maxw mt-[25px]'>
        <div className='sec-body'>
          <div className='relative w-full max-w-[480px] mx-auto text-center'>
            <form onSubmit={handleSubmit(onSubmit, onError)}>
              <input
                {...register('guardianName')}
                type='text'
                id='owner_name'
                className='input'
                placeholder='이름을 입력 해주세요.'></input>
              <div className='flex flex-wrap mt-4 text-center'>
                <div className='relative flex w-full spec-input'>
                  <input
                    type='text'
                    className=' short-input input !p-0 !border-r-0'
                    value='010'
                    placeholder='010'
                    readOnly={true}
                  />
                  <input
                    {...register('phoneNumber')}
                    type='text'
                    id='phone_number'
                    maxLength={9}
                    placeholder='-없이 휴대폰 번호를 입력 해주세요.'
                    className='input !border-l-0'
                    onChange={handlePhoneNumberChange}
                  />
                </div>
              </div>
              <button className='bg-green-main text-white mt-[53px] mb-[18px] btn-check-reserve md:mt-[46px]'>예약조회</button>
              <a href='/reservation' className='mx-auto text-center text-[14px] text-gray-400'>
                <span>{'<'}</span>&nbsp;
                <span className='relative'>
                  <span className='underline-offset-4 underline'>{'이전 페이지로 이동'}</span>
                </span>
              </a>
            </form>
          </div>
        </div>
      </div>
      <Modal isOpen={isOpenError} onClose={() => setIsOpenError(false)} isCentered>
        <ModalOverlay />
        <ModalContent className=' mx-[25px] md:mx-0'>
          <ModalHeader className='text-center !text-[16px]'>{getErrorMessages(errors)}</ModalHeader>
          <ModalBody className='text-center text-[14px] text-grey-main'>
            <button onClick={() => setIsOpenError(false)}>확인</button>
          </ModalBody>
        </ModalContent>
      </Modal>
    </div>
  );
};

export default ReservationPage;
