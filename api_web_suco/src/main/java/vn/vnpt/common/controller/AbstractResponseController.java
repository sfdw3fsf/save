package vn.vnpt.common.controller;

import vn.vnpt.common.response.ResponseEntites;

public abstract class AbstractResponseController {

	@SuppressWarnings("rawtypes")
	private final ResponseEntites responseEntites;

	@SuppressWarnings("rawtypes")
	protected AbstractResponseController(ResponseEntites responseEntites) {
		this.responseEntites = responseEntites;
	}
//
//	@SuppressWarnings("unchecked")
//	public DeferredResult<ResponseEntity<?>> responseEntityDeferredResult(CallbackFunction<?> callbackFunction) {
//		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(ConstantString.TIMEOUT_NONBLOCK);
//
//		deferredResult.onTimeout(() -> deferredResult.setErrorResult(
//				responseEntites.createErrorResponse(HttpStatus.REQUEST_TIMEOUT, ErrorCode.IDG_00000408)));
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		ForkJoinPool forkJoinPool = new ForkJoinPool();
//		forkJoinPool.submit(() -> {
//			try {
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//				deferredResult.setResult(responseEntites.createSuccessResponse(HttpStatus.OK,
//						new ApiResult<>(ErrorCode.IDG_00000000, callbackFunction.execute())));
//			} catch (CommonException e) {
//				// TODO: Co the can thay doi de phu hop
//				deferredResult.setResult(responseEntites.createErrorResponse(e.getApiError().getStatus(),
//						e.getApiError().getMessage(), e.getApiError().getError()));
//			} catch (Exception ex) {
//				deferredResult.setResult(responseEntites.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
//						ErrorCode.IDG_00000500, Common.subString(ex.toString().trim())));
//			}
//		});
//		return deferredResult;
//	}
}
