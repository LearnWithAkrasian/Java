package com.imran.dto;


import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Vector;

// Retrieves data from RESTAPI page.
public class RestApiDTO {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String startTime;
    @NotEmpty
    private String endingTime;
    private Vector <PayloadDTO> payloadDTOS;
    private boolean status;

    public Vector<PayloadDTO> getPayloadDTOS() {
        return payloadDTOS;
    }

    public void setPayloadDTOS(Vector<PayloadDTO> payloadDTOS) {
        this.payloadDTOS = payloadDTOS;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

//    @Override
//    public String toString() {
//        StringBuilder jsonObj = new StringBuilder(
//                String.format(
//                        "\"status\" : %s,\n " +
//                                "\"user_id\" : %s,\n" +
//                                "\"payload\" : [\n", isStatus(), getUserId()));
//        for (Map.Entry<LocalDateTime, Vector < Integer > > map : getListMap().entrySet()) {
//            String time = map.getKey().toString();
//            jsonObj.append("{\n\"timestamp\": \"").append(time).append("\",\n\"input_values\" : \"");
//            Vector < Integer > list = map.getValue();
//            int len = list.size();
//            for (int i = 0; i < len; i++) {
//                if (i == len-1)
//                    jsonObj.append(list.get(i)).append("\"\n");
//                else
//                    jsonObj.append(list.get(i)).append(",");
//            }
//            jsonObj.append("}\n");
//        }
//        return jsonObj.toString();
//
//    }
}
